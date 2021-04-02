package com.example.javaserver.study.service;

import com.example.javaserver.common_data.model.StudyGroup;
import com.example.javaserver.common_data.model.Subject;
import com.example.javaserver.common_data.model.SubjectSemester;
import com.example.javaserver.common_data.repo.SubjectRepo;
import com.example.javaserver.common_data.repo.SubjectSemesterRepo;
import com.example.javaserver.general.criteria.SearchCriteria;
import com.example.javaserver.general.model.Message;
import com.example.javaserver.general.model.UserContext;
import com.example.javaserver.general.model.UserDetailsImp;
import com.example.javaserver.general.specification.CommonSpecification;
import com.example.javaserver.study.controller.dto.TaskIn;
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

    @SuppressWarnings("Duplicates")
    @Transactional
    public Message create(TaskIn taskIn, UserDetailsImp userDetails) {
        if (taskIn.title == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Задание должно иметь заголовок");
        }
        if (taskIn.type == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Задание должно иметь тип");
        }
        if (taskIn.semesterId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Задание должно быть привязано к семестру");
        }

        Optional<SubjectSemester> semester = semesterRepo.findById(taskIn.semesterId);
        if (!semester.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Семестр с указанным id не существует");
        }

        Optional<User> user = userRepo.findById(userDetails.getId());
        if (!user.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "UserId токена инвалидный");
        }

        Set<UserFile> userFiles = userFileRepo.getUserFilesByIdIn(taskIn.fileIds);
        for (Long id : taskIn.fileIds) {
            if (userFiles.stream().noneMatch(f -> f.getId().equals(id))) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Файл с id = \" + id + \" не найден");
            }
        }
        for (UserFile file : userFiles) {
            if (file.getTask() != null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Невозможно создать задание: Файл с id = \" + file.getId() + \" привязан к другому заданию");
            }
        }

        Task task = new Task();
        task.setTitle(taskIn.title);
        task.setType(taskIn.type);
        task.setDescription(taskIn.description);
        task.setSemester(semester.get());
        task.setUser(user.get());
        userFiles.forEach(f -> f.setTask(task));
        taskRepo.save(task);

        return new Message("Задание успешно создано");
    }

    public Message delete(Set<Long> ids) {
        taskRepo.deleteAllByIdIn(ids);
        return new Message("Найденные задания были успешно удалены");
    }

    @Transactional
    public Message update(TaskIn taskIn) {
        Optional<Task> taskO = taskRepo.findById(taskIn.id);
        if (!taskO.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Задание с указанным id не существует");
        }
        Task task = taskO.get();

        if (task.getTitle() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Задание должно иметь заголовок");
        } else if (!Objects.equals(task.getTitle(), taskIn.title)) {
            task.setTitle(taskIn.title);
        }

        if (task.getType() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Задание должно иметь тип");
        } else if (!Objects.equals(task.getType(), taskIn.type)) {
            task.setType(taskIn.type);
        }

        if (!Objects.deepEquals(task.getDescription(), taskIn.description)) {
            task.setDescription(taskIn.description);
        }

        if (task.getSemester() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Задание должно быть привязано к семестру");
        } else if (!Objects.equals(task.getSemester().getId(), taskIn.semesterId)) {
            Optional<SubjectSemester> semester = semesterRepo.findById(taskIn.semesterId);
            if (!semester.isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Невозможно изменить задание: семестр с указанным id не существует");
            }
            task.setSemester(semester.get());
        }

        if (taskIn.fileIds == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Невозможно изменить задание: fileIds должно быть не null");
        }
        Set<UserFile> filesToRemove = new HashSet<>();
        task.getUserFiles().forEach(f -> {
            if (!taskIn.fileIds.contains(f.getId())) {
                filesToRemove.add(f);
            }
        });
        if (!filesToRemove.isEmpty()) {
            filesToRemove.forEach(f -> f.setTask(null));
        }
        Set<Long> fileToAddIds = new HashSet<>();
        taskIn.fileIds.forEach(i -> {
            if (task.getUserFiles().stream().noneMatch(f -> f.getId().equals(i))) {
                fileToAddIds.add(i);
            }
        });
        if (!fileToAddIds.isEmpty()) {
            Set<UserFile> filesToAdd = userFileRepo.getUserFilesByIdIn(fileToAddIds);
            for (Long id : fileToAddIds) {
                if (filesToAdd.stream().noneMatch(f -> f.getId().equals(id))) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Невозможно изменить задание: Файл с id = \" + id + \" не найден");
                }
            }
            for (UserFile file : filesToAdd) {
                if (file.getTask() != null) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Невозможно изменить задание: Файл с id = \" + file.getId() + \" привязан к другому заданию");
                    }
            }
            filesToAdd.forEach(f -> f.setTask(task));
        }

        if (taskIn.workIds == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Невозможно изменить задание: workIds должно быть не null");
        }
        Set<Work> worksToRemove = new HashSet<>();
        task.getWorks().forEach(w -> {
            if (!taskIn.workIds.contains(w.getId())) {
                worksToRemove.add(w);
            }
        });
        if (!worksToRemove.isEmpty()) {
            worksToRemove.forEach(f -> f.setTask(null));
        }
        Set<Long> workToAddIds = new HashSet<>();
        taskIn.workIds.forEach(i -> {
            if (task.getWorks().stream().noneMatch(w -> w.getId().equals(i))) {
                workToAddIds.add(i);
            }
        });
        if (!workToAddIds.isEmpty()) {
            Set<Work> worksToAdd = workRepo.getWorksByIdIn(workToAddIds);
            for (Long id : taskIn.workIds) {
                if (worksToAdd.stream().noneMatch(w -> w.getId().equals(id))) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Невозможно изменить задание: Работа с id = " + id + " не найдена");
                }
            }
            for (Work work : worksToAdd) {
                if (work.getTask() != null) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Невозможно изменить задание: Работа с id = \" + work.getId() + \" привязана к другому заданию");
                    }
            }
            worksToAdd.forEach(f -> f.setTask(task));
        }

        return new Message("Задание было успешно изменено");
    }

    public  List<Task> getAll() {
        List<Task> tasks = taskRepo.findAll(null);
        return tasks;
    }

    public List<Task> criteriaSearch(Set<SearchCriteria> criteria) {
        try {
            Specification<Task> specification = CommonSpecification.of(criteria);
            List<Task> tasks = taskRepo.findAll(specification);
            return tasks;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Критерии поиска некорректны");
        }
    }

    public  List<Task> searchByIds(Set<Long> ids) {
        List<Task> tasks = (List<Task>) taskRepo.findAllByIdIn(ids);
        return tasks;
    }

    public List<Task> searchBySubjectAndUser(Long subjectId, Integer userId, UserDetailsImp userDetails) {
        if (userId == null) {
            userId = userDetails.getId();
        }

        Optional<User> user = userRepo.findById(userId);
        if (!user.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id пользователя инвалидный");
        }

        StudyGroup group = user.get().getStudyGroup();
        if (group == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Студент не привязан к группе");
        }

        Set<SubjectSemester> semesters = group.getSubjectSemesters();
        if (semesters.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Группа, в которой состоит студент не имеет семестров");
        }

        Optional<SubjectSemester> semester = semesters.stream().filter(s -> subjectId.equals(s.getSubject().getId())).findFirst();
        if (!semester.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Предмета с указанным id нет у пользователя");
        }

        return (List<Task>) semester.get().getTasks();
    }
}
