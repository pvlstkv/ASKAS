package com.example.javaserver.study.service;

import com.example.javaserver.common_data.service.StudyGroupService;
import com.example.javaserver.general.criteria.SearchCriteria;
import com.example.javaserver.general.model.Message;
import com.example.javaserver.general.model.UserDetailsImp;
import com.example.javaserver.general.specification.CommonSpecification;
import com.example.javaserver.study.model.Task;
import com.example.javaserver.study.model.UserFile;
import com.example.javaserver.study.model.Work;
import com.example.javaserver.study.repo.WorkRepo;
import com.example.javaserver.user.model.User;
import com.example.javaserver.user.model.UserRole;
import com.example.javaserver.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings("Duplicates")
@Service
public class WorkService {
    private final WorkRepo workRepo;
    private final UserService userService;
    private final StudyGroupService groupService;

    @Autowired
    public WorkService(WorkRepo workRepo, UserService userService, StudyGroupService groupService) {
        this.workRepo = workRepo;
        this.userService = userService;
        this.groupService = groupService;
    }

    @SuppressWarnings("Duplicates")
    @Transactional
    public Work create(Work work, UserDetailsImp userDetails) {
        if (work.getStudentComment() == null && (work.getUserFiles() == null || work.getUserFiles().isEmpty())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Нельзя добавлять работу без файлов и комментариев");
        }

        User user = userService.getById(userDetails.getId());

        work.setUser(user);
        if (user.getRole().equals(UserRole.USER)) {
            work.setTeacherComment(null);
            work.setMark(null);
        }

        return workRepo.save(work);
    }

    public Message delete(Set<Long> ids) {
        workRepo.deleteAllByIdIn(ids);
        return new Message("Найденные работы были успешно удалены");
    }

    @Transactional
    public Work update(Long id, Work workToPut) {
        Optional<Work> workO = workRepo.findById(id);
        if (workO.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Работа с указанным id не найдена");
        }
        Work work = workO.get();

        if (workToPut.getStudentComment() == null && (workToPut.getUserFiles() == null || workToPut.getUserFiles().isEmpty())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Нельзя сделать работу без файлов и комментариев");
        }

        Task task = workToPut.getTask();
        Set<UserFile> userFiles = workToPut.getUserFiles();

        work.setTask(task);
        work.setUserFiles(userFiles);
        work.setStudentComment(workToPut.getStudentComment());
        work.setTeacherComment(workToPut.getTeacherComment());
        work.setMark(workToPut.getMark());

        return work;
    }

    public Collection<Work> getAll() {
        return workRepo.findAll(null);
    }

    public Collection<Work> criteriaSearch(Set<SearchCriteria> criteria) {
        try {
            Specification<Work> specification = CommonSpecification.of(criteria);
            return workRepo.findAll(specification);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Критерии поиска некорректны");
        }
    }

    public Work getById(Long id) {
        Optional<Work> workO = workRepo.findByIdEquals(id);
        if (workO.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Работа с указанным id не существует");
        }
        return workO.get();
    }

    public Set<Work> getByIds(Set<Long> ids) {
        Set<Work> works = workRepo.getWorksByIdIn(ids);
        if (works.size() == ids.size()) {
            return works;
        } else {
            Collection<Long> foundIds = works.stream()
                    .map(Work::getId)
                    .collect(Collectors.toSet());
            Collection<Long> notFoundIds = ids.stream()
                    .filter(i -> !foundIds.contains(i))
                    .collect(Collectors.toSet());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Работы с id: " + Arrays.toString(notFoundIds.toArray()) + " не существуют");
        }
    }

    public Collection<Work> searchByGroupsAndTeacher(Integer userId, Long groupId, UserDetailsImp userDetails) {
        if (userId == null) {
            userId = userDetails.getId();
        }

        User user = userService.getById(userId);
        if (user.getRole() != UserRole.TEACHER) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Пользователь не является преподавателем");
        }

        return groupService.getById(groupId)
                .getSubjectSemesters().stream()
                .filter(sem -> sem.getSubject().getTeachers().contains(user))
                .flatMap(sem -> sem.getTasks().stream())
                .flatMap(t -> t.getWorks().stream())
                .collect(Collectors.toSet());
    }
}
