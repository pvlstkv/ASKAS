package com.example.javaserver.study.service;

import com.example.javaserver.common_data.model.SubjectSemester;
import com.example.javaserver.general.criteria.SearchCriteria;
import com.example.javaserver.general.model.Message;
import com.example.javaserver.general.model.UserContext;
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
    public ResponseEntity<?> create(WorkIn workIn, UserContext userContext) {
        if (workIn.taskId == null) {
            return new ResponseEntity<>(new Message("TaskId должен быть не null"), HttpStatus.BAD_REQUEST);
        }

        if (workIn.studentComment == null && workIn.fileIds == null) {
            return new ResponseEntity<>(new Message("Нельзя добавлять работу без файлов и комментариев"), HttpStatus.BAD_REQUEST);
        }

        Optional<Task> task = taskRepo.findById(workIn.taskId);
        if (!task.isPresent()) {
            return new ResponseEntity<>(new Message("Задание с указанным id не существует"), HttpStatus.BAD_REQUEST);
        }

        Set<UserFile> userFiles = userFileRepo.getUserFilesByIdIn(workIn.fileIds);
        for (Long id : workIn.fileIds) {
            if (userFiles.stream().noneMatch(f -> f.getId().equals(id))) {
                return new ResponseEntity<>(new Message("Файл с id = " + id + " не найден"), HttpStatus.BAD_REQUEST);
            }
        }

        Optional<User> user = userRepo.findById(userContext.getUserId());
        if (!user.isPresent()) {
            return new ResponseEntity<>(new Message("UserId токена инвалидный"), HttpStatus.BAD_REQUEST);
        }

        Work work = new Work();
        work.setTask(task.get());
        work.setUser(user.get());
        work.setStudentComment(workIn.studentComment);
        userFiles.forEach(f -> f.setWork(work));
        workRepo.save(work);

        return new ResponseEntity<>(new Message("Работа успешно создана"), HttpStatus.CREATED);
    }

    public ResponseEntity<?> delete(Set<Long> ids) {
        workRepo.deleteAllByIdIn(ids);
        return new ResponseEntity<>(new Message("Найденные работы были успешно удалены"), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> update(WorkIn workIn) {
        Optional<Work> workO = workRepo.findById(workIn.id);
        if (!workO.isPresent()) {
            return new ResponseEntity<>(new Message("Работа с указанным id не существует"), HttpStatus.BAD_REQUEST);
        }
        Work work = workO.get();

        if (workIn.studentComment == null && workIn.fileIds == null) {
            return new ResponseEntity<>(new Message("Нельзя сделать работу без файлов и комментариев"), HttpStatus.BAD_REQUEST);
        }

        if (work.getTask() == null) {
            return new ResponseEntity<>(new Message("Работа должна быть привязана к заданию"), HttpStatus.BAD_REQUEST);
        } else if (!work.getTask().getId().equals(workIn.taskId)) {
            Optional<Task> task = taskRepo.findById(workIn.taskId);
            if (!task.isPresent()) {
                return new ResponseEntity<>(new Message("Невозможно изменить задание: задание с указанным id не существует"), HttpStatus.BAD_REQUEST);
            }
            work.setTask(task.get());
        }

        if (!work.getStudentComment().equals(workIn.studentComment)) {
            work.setStudentComment(workIn.studentComment);
        }

        if (!work.getTeacherComment().equals(workIn.teacherComment)) {
            work.setTeacherComment(workIn.teacherComment);
        }

        if (work.getMark().equals(workIn.mark)) {
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
                    return new ResponseEntity<>(new Message("Невозможно изменить работу: Файл с id = " + id + " не найден"), HttpStatus.BAD_REQUEST);
                }
            }
            for (UserFile file : filesToAdd) {
                if (file.getWork() != null) {
                    return new ResponseEntity<>(new Message("Невозможно изменить работу: Файл с id = " + file.getId() + " привязан к другой работе"), HttpStatus.BAD_REQUEST);
                }
            }
            filesToAdd.forEach(f -> f.setWork(work));
        }

        return new ResponseEntity<>(new Message("Работа была успешно изменена"), HttpStatus.OK);
    }

    public ResponseEntity<?> getAll() {
        Collection<Work> works = workRepo.findAll(null);
        return new ResponseEntity<>(works, HttpStatus.OK);
    }

    public ResponseEntity<?> criteriaSearch(Set<SearchCriteria> criteria) {
        try {
            Specification<Work> specification = CommonSpecification.of(criteria);
            List<Work> works = workRepo.findAll(specification);
            return new ResponseEntity<>(works, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new Message("Критерии поиска некорректны"), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> searchByIds(Set<Long> ids) {
        Collection<Work> works = workRepo.getWorksByIdIn(ids);
        return new ResponseEntity<>(works, HttpStatus.OK);
    }
}
