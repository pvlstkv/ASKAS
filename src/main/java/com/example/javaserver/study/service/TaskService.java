package com.example.javaserver.study.service;

import com.example.javaserver.common_data.model.StudyGroup;
import com.example.javaserver.common_data.model.Subject;
import com.example.javaserver.common_data.model.SubjectSemester;
import com.example.javaserver.common_data.repo.SubjectRepo;
import com.example.javaserver.general.criteria.SearchCriteria;
import com.example.javaserver.general.model.Message;
import com.example.javaserver.general.model.UserDetailsImp;
import com.example.javaserver.general.specification.CommonSpecification;
import com.example.javaserver.study.model.Task;
import com.example.javaserver.study.model.UserFile;
import com.example.javaserver.study.repo.TaskRepo;
import com.example.javaserver.user.model.User;
import com.example.javaserver.user.model.UserRole;
import com.example.javaserver.user.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings("Duplicates")
@Service
public class TaskService {
    private final TaskRepo taskRepo;
    private final UserRepo userRepo;
    private final SubjectRepo subjectRepo;

    @Autowired
    public TaskService(TaskRepo taskRepo, UserRepo userRepo, SubjectRepo subjectRepo) {
        this.taskRepo = taskRepo;
        this.userRepo = userRepo;
        this.subjectRepo = subjectRepo;
    }

    @SuppressWarnings("Duplicates")
    @Transactional
    public Task create(Task task, UserDetailsImp userDetails) {
        User user = userRepo.getOne(userDetails.getId());

        task.getUserFiles().forEach(UserFile::incLinkCount);

        task.setUser(user);
        return taskRepo.save(task);
    }

    public Message delete(Set<Long> ids) {
        taskRepo.deleteAllByIdIn(ids);
        return new Message("Найденные задания были успешно удалены");
    }

    @Transactional
    public Task update(Long id, Task taskToPut) {
        Task task = taskRepo.getOne(id);

        Set<UserFile> userFiles = taskToPut.getUserFiles();
        Set<SubjectSemester> semesters = taskToPut.getSemesters();

        task.getUserFiles().forEach(UserFile::decLinkCount);
        userFiles.forEach(UserFile::incLinkCount);

        task.setType(taskToPut.getType());
        task.setTitle(taskToPut.getTitle());
        task.setDescription(taskToPut.getDescription());
        task.setSemesters(semesters);
        task.setUserFiles(userFiles);

        return task;
    }

    public  Collection<Task> getAll() {
        return taskRepo.findAll(null);
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
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id пользователя инвалидный");
        }
        if (!user.get().getRole().equals(UserRole.USER)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Пользователь не является студентом");
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
        if (semester.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Предмета с указанным id нет у пользователя");
        }

        return semester.get().getTasks();
    }

    public Collection<Task> searchBySubject(Long subjectId) {
        Optional<Subject> subject = subjectRepo.findById(subjectId);
        if (subject.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Предмета с указанным id не существует");
        }

        return subject.get()
                .getSemesters().stream()
                .flatMap(sem -> sem.getTasks().stream())
                .collect(Collectors.toSet());
    }
}
