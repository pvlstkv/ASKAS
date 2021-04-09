package com.example.javaserver.study.service;

import com.example.javaserver.common_data.model.StudyGroup;
import com.example.javaserver.common_data.model.Subject;
import com.example.javaserver.common_data.model.SubjectSemester;
import com.example.javaserver.common_data.repo.SubjectRepo;
import com.example.javaserver.common_data.repo.SubjectSemesterRepo;
import com.example.javaserver.general.criteria.SearchCriteria;
import com.example.javaserver.general.model.Message;
import com.example.javaserver.general.model.UserDetailsImp;
import com.example.javaserver.general.specification.CommonSpecification;
import com.example.javaserver.study.controller.dto.LiteratureIn;
import com.example.javaserver.study.model.Task;
import com.example.javaserver.study.model.TaskType;
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
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LiteratureService {

    private final TaskRepo taskRepo;
    private final UserFileRepo userFileRepo;
    private final WorkRepo workRepo;
    private final SubjectSemesterRepo semesterRepo;
    private final UserRepo userRepo;
    private final SubjectRepo subjectRepo;

    @Autowired
    public LiteratureService(TaskRepo taskRepo, UserFileRepo userFileRepo, WorkRepo workRepo, SubjectSemesterRepo semesterRepo, UserRepo userRepo, SubjectRepo subjectRepo) {
        this.taskRepo = taskRepo;
        this.userFileRepo = userFileRepo;
        this.workRepo = workRepo;
        this.semesterRepo = semesterRepo;
        this.userRepo = userRepo;
        this.subjectRepo = subjectRepo;
    }

    @SuppressWarnings("Duplicates")
    @Transactional
    public Message create(LiteratureIn literatureIn, UserDetailsImp userDetails) {
        if(literatureIn.type != TaskType.LITERATURE){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Неверный тип задания");
        }
        if (literatureIn.title == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Литература должна иметь заголовок");
        }
        if (literatureIn.type == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Литература должна иметь тип");
        }

        Collection<SubjectSemester> semesters = semesterRepo.findAllByIdIn(literatureIn.semesterIds);
        for (Long id : literatureIn.semesterIds) {
            if (semesters.stream().noneMatch(f -> f.getId().equals(id))) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Семестр с id = \"" + id + "\" не найден");
            }
        }

        Optional<User> user = userRepo.findById(userDetails.getId());
        if (!user.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "UserId токена инвалидный");
        }

        Collection<UserFile> userFiles = userFileRepo.getUserFilesByIdIn(literatureIn.fileIds);
        for (Long id : literatureIn.fileIds) {
            if (userFiles.stream().noneMatch(f -> f.getId().equals(id))) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Файл с id = \"" + id + "\" не найден");
            }
        }
        for (UserFile file : userFiles) {
            if (file.getTask() != null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Невозможно создать Литературу: Файл с id = \" + file.getId() + \" привязан к другому заданию");
            }
        }

        Task task = new Task();
        task.setTitle(literatureIn.title);
        task.setType(literatureIn.type);
        task.setDescription(literatureIn.description);
        task.setUser(user.get());
        semesters.forEach(s -> s.getTasks().add(task));
        userFiles.forEach(f -> f.setTask(task));
        taskRepo.save(task);

        return new Message("Литература успешна создано");
    }

    public Message delete(Set<Long> ids) {
        taskRepo.deleteAllByIdIn(ids);
        return new Message("Найденная литература была успешно удалены");
    }

    @Transactional
    public Message update(LiteratureIn literatureIn) {
        Optional<Task> taskO = taskRepo.findById(literatureIn.id);
        if(literatureIn.type != TaskType.LITERATURE){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Неверный тип литературу");
        }
        if (!taskO.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Task task = taskO.get();

        if (task.getTitle() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Литература должна иметь заголовок");
        } else if (!Objects.equals(task.getTitle(), literatureIn.title)) {
            task.setTitle(literatureIn.title);
        }

        if (task.getType() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Литература должна иметь тип");
        } else if (!Objects.equals(task.getType(), literatureIn.type)) {
            task.setType(literatureIn.type);
        }

        if (!Objects.deepEquals(task.getDescription(), literatureIn.description)) {
            task.setDescription(literatureIn.description);
        }

        if (literatureIn.semesterIds == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Невозможно изменить литературу: semesterIds должно быть не null");
        }
        Set<SubjectSemester> semestersToRemove = new HashSet<>();
        task.getSemesters().forEach(s -> {
            if (!literatureIn.semesterIds.contains(s.getId())) {
                semestersToRemove.add(s);
            }
        });
        if (!semestersToRemove.isEmpty()) {
            semestersToRemove.forEach(s -> s.getTasks().remove(task));
        }
        Set<Long> semesterToAddIds = new HashSet<>();
        literatureIn.semesterIds.forEach(i -> {
            if (task.getSemesters().stream().noneMatch(s -> s.getId().equals(i))) {
                semesterToAddIds.add(i);
            }
        });
        if (!semesterToAddIds.isEmpty()) {
            Collection<SubjectSemester> semestersToAdd = semesterRepo.findAllByIdIn(semesterToAddIds);
            for (Long id : semesterToAddIds) {
                if (semestersToAdd.stream().noneMatch(s -> s.getId().equals(id))) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Невозможно изменить литературу: Семестр с id = \"" + id + "\" не найден");
                }
            }
            semestersToAdd.forEach(s -> s.getTasks().add(task));
        }

        if (literatureIn.fileIds == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Невозможно изменить литературу: fileIds должно быть не null");
        }
        Set<UserFile> filesToRemove = new HashSet<>();
        task.getUserFiles().forEach(f -> {
            if (!literatureIn.fileIds.contains(f.getId())) {
                filesToRemove.add(f);
            }
        });
        if (!filesToRemove.isEmpty()) {
            filesToRemove.forEach(f -> f.setTask(null));
        }
        Set<Long> fileToAddIds = new HashSet<>();
        literatureIn.fileIds.forEach(i -> {
            if (task.getUserFiles().stream().noneMatch(f -> f.getId().equals(i))) {
                fileToAddIds.add(i);
            }
        });
        if (!fileToAddIds.isEmpty()) {
            Set<UserFile> filesToAdd = userFileRepo.getUserFilesByIdIn(fileToAddIds);
            for (Long id : fileToAddIds) {
                if (filesToAdd.stream().noneMatch(f -> f.getId().equals(id))) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Невозможно изменить литературу: Файл с id = \" + id + \" не найден");
                }
            }
            for (UserFile file : filesToAdd) {
                if (file.getTask() != null) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Невозможно изменить литературу: Файл с id = \" + file.getId() + \" привязан к другому заданию");
                }
            }
            filesToAdd.forEach(f -> f.setTask(task));
        }

        return new Message("Задание было успешно изменено");
    }

    public  Collection<Task> getAll() {
        Collection<Task> tasks = taskRepo.findAll(null);
        tasks = tasks.stream().filter(task -> task.getType().equals(TaskType.LITERATURE)).collect(Collectors.toList());
        return tasks;
    }

    public Collection<Task> criteriaSearch(Set<SearchCriteria> criteria) {
        try {
            Specification<Task> specification = CommonSpecification.of(criteria);
            return taskRepo.findAll(specification);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Критерии поиска некорректны");
        }
    }

    public  Collection<Task> searchByIds(Set<Long> ids) {
        return taskRepo.findAllByIdIn(ids);
    }

    public Collection<Task> searchBySubjectAndStudent(Long subjectId, Integer userId, UserDetailsImp userDetails) {

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

        //фильтр чтобы литература не возвращалась
        Collection<Task> tasks = semester.get().getTasks();
        tasks = tasks.stream().filter(task -> task.getType() != TaskType.LITERATURE).collect(Collectors.toList());
        return tasks;
    }

    public Collection<Task> searchBySubjectAndTeacher(Long subjectId) {
        Optional<Subject> subject = subjectRepo.findById(subjectId);
        if (!subject.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Литературы с указанным id не существует");
        }

        //фильтр чтобы литература не возвращалась
        return subject.get()
                .getSemesters().stream()
                .flatMap(sem -> sem.getTasks().stream())
                .filter(task -> task.getType().equals(TaskType.LITERATURE))
                .collect(Collectors.toSet());
    }
}
