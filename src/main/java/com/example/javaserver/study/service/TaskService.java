package com.example.javaserver.study.service;

import com.example.javaserver.common_data.model.Faculty;
import com.example.javaserver.common_data.model.SubjectSemester;
import com.example.javaserver.common_data.repo.SubjectSemesterRepo;
import com.example.javaserver.general.criteria.SearchCriteria;
import com.example.javaserver.general.model.Message;
import com.example.javaserver.general.model.UserContext;
import com.example.javaserver.general.specification.CommonSpecification;
import com.example.javaserver.study.controller.dto.TaskIn;
import com.example.javaserver.study.model.Task;
import com.example.javaserver.study.model.UserFile;
import com.example.javaserver.study.model.Work;
import com.example.javaserver.study.repo.UserFileRepo;
import com.example.javaserver.study.repo.TaskRepo;
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
public class TaskService {
    private final TaskRepo taskRepo;
    private final UserFileRepo userFileRepo;
    private final WorkRepo workRepo;
    private final SubjectSemesterRepo semesterRepo;
    private final UserRepo userRepo;

    @Autowired
    public TaskService(TaskRepo taskRepo, UserFileRepo userFileRepo, WorkRepo workRepo, SubjectSemesterRepo semesterRepo, UserRepo userRepo) {
        this.taskRepo = taskRepo;
        this.userFileRepo = userFileRepo;
        this.workRepo = workRepo;
        this.semesterRepo = semesterRepo;
        this.userRepo = userRepo;
    }

    @Transactional
    public ResponseEntity<?> create(TaskIn taskIn, UserContext userContext) {
        if (taskIn.title == null) {
            return new ResponseEntity<>(new Message("Задание должно иметь заголовок"), HttpStatus.BAD_REQUEST);
        }
        if (taskIn.type == null) {
            return new ResponseEntity<>(new Message("Задание должно иметь тип"), HttpStatus.BAD_REQUEST);
        }
        if (taskIn.semesterId == null) {
            return new ResponseEntity<>(new Message("Задание должно быть привязано к семестру"), HttpStatus.BAD_REQUEST);
        }

        Optional<SubjectSemester> semester = semesterRepo.findById(taskIn.semesterId);
        if (!semester.isPresent()) {
            return new ResponseEntity<>(new Message("Семестр с указанным id не существует"), HttpStatus.BAD_REQUEST);
        }

        Optional<User> user = userRepo.findById(userContext.getUserId());
        if (!user.isPresent()) {
            return new ResponseEntity<>(new Message("UserId токена инвалидный"), HttpStatus.BAD_REQUEST);
        }

        Set<UserFile> userFiles = userFileRepo.getUserFilesByIdIn(taskIn.fileIds);
        for (Long id : taskIn.fileIds) {
            if (userFiles.stream().noneMatch(f -> f.getId().equals(id))) {
                return new ResponseEntity<>(new Message("Файл с id = " + id + " не найден"), HttpStatus.BAD_REQUEST);
            }
        }

        Task task = new Task();
        task.setTitle(taskIn.title);
        task.setType(taskIn.type);
        task.setDescription(taskIn.description);
        task.setSemester(semester.get());
        task.setUser(user.get());
        task.setFiles(userFiles);

        taskRepo.save(task);

        return new ResponseEntity<>(new Message("Задание успешно создано"), HttpStatus.CREATED);
    }

    public ResponseEntity<?> delete(Set<Long> ids) {
        taskRepo.deleteAllByIdIn(ids);
        return new ResponseEntity<>(new Message("Найденные задания были успешно удалены"), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> update(TaskIn taskIn) {
        Optional<Task> taskO = taskRepo.findById(taskIn.id);
        if (!taskO.isPresent()) {
            return new ResponseEntity<>(new Message("Задание с указанным id не существует"), HttpStatus.BAD_REQUEST);
        }
        Task task = taskO.get();

        if (!task.getTitle().equals(taskIn.title)) {
            task.setTitle(taskIn.title);
        }

        if (!task.getType().equals(taskIn.type)) {
            task.setType(taskIn.type);
        }

        if (!task.getDescription().equals(taskIn.description)) {
            task.setDescription(taskIn.description);
        }

        if (!task.getSemester().getId().equals(taskIn.semesterId)) {
            if (taskIn.semesterId == null) {
                task.setSemester(null);
            } else {
                Optional<SubjectSemester> semester = semesterRepo.findById(taskIn.semesterId);
                if (!semester.isPresent()) {
                    return new ResponseEntity<>(new Message("Невозможно изменить задание: семестр с указанным id не существует"), HttpStatus.BAD_REQUEST);
                }
                task.setSemester(semester.get());
            }
        }

        if (taskIn.fileIds == null) {
            return new ResponseEntity<>(new Message("Невозможно изменить задание: fileIds должно быть не null"), HttpStatus.BAD_REQUEST);
        }
        Set<UserFile> filesToRemove = new HashSet<>();
        task.getFiles().forEach(f -> {
            if (!taskIn.fileIds.contains(f.getId())) {
                filesToRemove.add(f);
            }
        });
        if (filesToRemove.isEmpty()) {
            task.getFiles().removeAll(filesToRemove);
        }
        Set<Long> fileToAddIds = new HashSet<>();
        taskIn.fileIds.forEach(i -> {
            if (task.getFiles().stream().noneMatch(f -> f.getId().equals(i))) {
                fileToAddIds.add(i);
            }
        });
        if (fileToAddIds.isEmpty()) {
            Set<UserFile> filesToAdd = userFileRepo.getUserFilesByIdIn(fileToAddIds);
            for (Long id : taskIn.fileIds) {
                if (filesToAdd.stream().noneMatch(f -> f.getId().equals(id))) {
                    return new ResponseEntity<>(new Message("Невозможно изменить задание: Файл с id = " + id + " не найден"), HttpStatus.BAD_REQUEST);
                }
            }
            task.getFiles().addAll(filesToAdd);
        }

        if (taskIn.workIds == null) {
            return new ResponseEntity<>(new Message("Невозможно изменить задание: workIds должно быть не null"), HttpStatus.BAD_REQUEST);
        }
        Set<Work> worksToRemove = new HashSet<>();
        task.getWorks().forEach(w -> {
            if (!taskIn.workIds.contains(w.getId())) {
                worksToRemove.add(w);
            }
        });
        if (worksToRemove.isEmpty()) {
            task.getWorks().removeAll(worksToRemove);
        }
        Set<Long> workToAddIds = new HashSet<>();
        taskIn.workIds.forEach(i -> {
            if (task.getWorks().stream().noneMatch(w -> w.getId().equals(i))) {
                workToAddIds.add(i);
            }
        });
        if (workToAddIds.isEmpty()) {
            Set<Work> worksToAdd = workRepo.getWorksByIdIn(workToAddIds);
            for (Long id : taskIn.workIds) {
                if (worksToAdd.stream().noneMatch(w -> w.getId().equals(id))) {
                    return new ResponseEntity<>(new Message("Невозможно изменить задание: Работа с id = " + id + " не найдена"), HttpStatus.BAD_REQUEST);
                }
            }
            task.getWorks().addAll(worksToAdd);
        }

        return new ResponseEntity<>(new Message("Задание было успешно изменено"), HttpStatus.OK);
    }

    public ResponseEntity<?> getAll() {
        Collection<Task> tasks = taskRepo.findAll(null);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    public ResponseEntity<?> criteriaSearch(Set<SearchCriteria> criteria) {
        try {
            Specification<Task> specification = CommonSpecification.of(criteria);
            List<Task> tasks = taskRepo.findAll(specification);
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new Message("Критерии поиска некорректны"), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> searchByIds(Set<Long> ids) {
        Collection<Task> tasks = taskRepo.findAllByIdIn(ids);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }
}
