package com.example.javaserver.study.service;

import com.example.javaserver.common_data.model.SubjectSemester;
import com.example.javaserver.general.criteria.SearchCriteria;
import com.example.javaserver.general.model.Message;
import com.example.javaserver.general.model.UserContext;
import com.example.javaserver.general.model.UserDetailsImp;
import com.example.javaserver.general.specification.CommonSpecification;
import com.example.javaserver.study.controller.dto.WorkIn;
import com.example.javaserver.study.model.Task;
import com.example.javaserver.study.model.UserFile;
import com.example.javaserver.study.model.Work;
import com.example.javaserver.study.repo.TaskRepo;
import com.example.javaserver.study.repo.UserFileRepo;
import com.example.javaserver.study.repo.WorkRepo;
import com.example.javaserver.user.model.User;
import com.example.javaserver.user.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class WorkService {
    private final WorkRepo workRepo;
    private final TaskRepo taskRepo;
    private final UserRepo userRepo;
    private final UserFileRepo userFileRepo;

    @Autowired
    public WorkService(WorkRepo workRepo, TaskRepo taskRepo, UserRepo userRepo, UserFileRepo userFileRepo) {
        this.workRepo = workRepo;
        this.taskRepo = taskRepo;
        this.userRepo = userRepo;
        this.userFileRepo = userFileRepo;
    }

    @SuppressWarnings("Duplicates")
    @Transactional
    public Message create(WorkIn workIn, UserDetailsImp userDetails) {
        if (workIn.taskId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "TaskId должен быть не null");
        }

        if (workIn.studentComment == null && workIn.fileIds == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Нельзя добавлять работу без файлов и комментариев");
        }

        Optional<Task> task = taskRepo.findById(workIn.taskId);
        if (!task.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Задание с указанным id не существует");
        }

        Set<UserFile> userFiles = userFileRepo.getUserFilesByIdIn(workIn.fileIds);
        for (Long id : workIn.fileIds) {
            if (userFiles.stream().noneMatch(f -> f.getId().equals(id))) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Файл с id = \" + id + \" не найден");
            }
        }
        for (UserFile file : userFiles) {
            if (file.getWork() != null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Невозможно создать работу: Файл с id = \" + file.getId() + \" привязан к другой работе");
            }
        }

        Optional<User> user = userRepo.findById(userDetails.getId());
        if (!user.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "UserId токена инвалидный");
        }

        Work work = new Work();
        work.setTask(task.get());
        work.setUser(user.get());
        work.setStudentComment(workIn.studentComment);
        userFiles.forEach(f -> f.setWork(work));
        workRepo.save(work);

        return new Message("Работа успешно создана");
    }

    public Message delete(Set<Long> ids) {
        workRepo.deleteAllByIdIn(ids);
        return new Message("Найденные работы были успешно удалены");
    }

    @Transactional
    public Message update(WorkIn workIn) {
        Optional<Work> workO = workRepo.findById(workIn.id);
        if (!workO.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Работа с указанным id не существует");
        }
        Work work = workO.get();

        if (workIn.studentComment == null && workIn.fileIds == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Нельзя сделать работу без файлов и комментариев");
        }

        if (workIn.taskId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Работа должна быть привязана к заданию");
        } else if (!Objects.equals(work.getTask().getId(), workIn.taskId)) {
            Optional<Task> task = taskRepo.findById(workIn.taskId);
            if (!task.isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Невозможно изменить задание: задание с указанным id не существует");
            }
            work.setTask(task.get());
        }

        if (!Objects.deepEquals(work.getStudentComment(), workIn.studentComment)) {
            work.setStudentComment(workIn.studentComment);
        }

        if (!Objects.deepEquals(work.getTeacherComment(), workIn.teacherComment)) {
            work.setTeacherComment(workIn.teacherComment);
        }

        if (!Objects.deepEquals(work.getMark(), workIn.mark)) {
            work.setMark(workIn.mark);
        }

        Set<UserFile> filesToRemove = new HashSet<>();
        work.getUserFiles().forEach(f -> {
            if (!workIn.fileIds.contains(f.getId())) {
                filesToRemove.add(f);
            }
        });
        if (!filesToRemove.isEmpty()) {
            filesToRemove.forEach(f -> f.setWork(null));
        }
        Set<Long> fileToAddIds = new HashSet<>();
        workIn.fileIds.forEach(i -> {
            if (work.getUserFiles().stream().noneMatch(f -> f.getId().equals(i))) {
                fileToAddIds.add(i);
            }
        });
        if (!fileToAddIds.isEmpty()) {
            Set<UserFile> filesToAdd = userFileRepo.getUserFilesByIdIn(fileToAddIds);
            for (Long id : fileToAddIds) {
                if (filesToAdd.stream().noneMatch(f -> f.getId().equals(id))) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Невозможно изменить работу: Файл с id = \" + id + \" не найден");
                }
            }
            for (UserFile file : filesToAdd) {
                if (file.getWork() != null) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Невозможно изменить работу: Файл с id = \" + file.getId() + \" привязан к другой работе");
                }
            }
            filesToAdd.forEach(f -> f.setWork(work));
        }
        return new Message("Работа была успешно изменена");
    }

    public List<Work> getAll() {
        List<Work> works = workRepo.findAll(null);
        return works;
    }

    public List<Work> criteriaSearch(Set<SearchCriteria> criteria) {
        try {
            Specification<Work> specification = CommonSpecification.of(criteria);
            List<Work> works = workRepo.findAll(specification);
            return works;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Критерии поиска некорректны");
        }
    }

    public List<Work> searchByIds(Set<Long> ids) {
        List<Work> works = (List<Work>) workRepo.getWorksByIdIn(ids);
        return works;
    }
}
