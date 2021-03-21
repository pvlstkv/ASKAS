package com.example.javaserver.study.service;

import com.example.javaserver.common_data.model.SubjectSemester;
import com.example.javaserver.common_data.repo.SubjectSemesterRepo;
import com.example.javaserver.general.model.Message;
import com.example.javaserver.general.model.UserContext;
import com.example.javaserver.study.controller.dto.TaskIn;
import com.example.javaserver.study.model.Task;
import com.example.javaserver.study.model.UserFile;
import com.example.javaserver.study.repo.UserFileRepo;
import com.example.javaserver.study.repo.TaskRepo;
import com.example.javaserver.user.model.User;
import com.example.javaserver.user.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;

@Service
public class TaskService {
    private final TaskRepo taskRepo;
    private final UserFileRepo userFileRepo;
    private final SubjectSemesterRepo semesterRepo;
    private final UserRepo userRepo;

    @Autowired
    public TaskService(TaskRepo taskRepo, UserFileRepo userFileRepo, SubjectSemesterRepo semesterRepo, UserRepo userRepo) {
        this.taskRepo = taskRepo;
        this.userFileRepo = userFileRepo;
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

    @Transactional
    public ResponseEntity<?> delete(Set<Long> ids) {
        taskRepo.deleteAllByIdIn(ids);
        return new ResponseEntity<>(new Message("Найденные задания были успешно удалены"), HttpStatus.OK);
    }
}
