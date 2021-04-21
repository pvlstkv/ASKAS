package com.example.javaserver.study.service;

import com.example.javaserver.common_data.model.StudyGroup;
import com.example.javaserver.common_data.repo.StudyGroupRepo;
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
public class WorkService {
    private final WorkRepo workRepo;
    private final UserRepo userRepo;
    private final StudyGroupRepo studyGroupRepo;

    @Autowired
    public WorkService(WorkRepo workRepo, UserRepo userRepo, StudyGroupRepo studyGroupRepo) {
        this.workRepo = workRepo;
        this.userRepo = userRepo;
        this.studyGroupRepo = studyGroupRepo;
    }

    @SuppressWarnings("Duplicates")
    @Transactional
    public Work create(Work work, UserDetailsImp userDetails) {
        if (work.getStudentComment() == null && (work.getUserFiles() == null || work.getUserFiles().isEmpty())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Нельзя добавлять работу без файлов и комментариев");
        }

        User user = userRepo.getOne(userDetails.getId());

        work.setUser(user);
        if (user.getRole().equals(UserRole.USER)) {
            work.setTeacherComment(null);
            work.setMark(null);
        }

        Set<UserFile> userFiles = work.getUserFiles();
        userFiles.forEach(UserFile::incLinkCount);

        return workRepo.save(work);
    }

    public Message delete(Set<Long> ids) {
        workRepo.deleteAllByIdIn(ids);
        return new Message("Найденные работы были успешно удалены");
    }

    @Transactional
    public Work update(Long id, Work workToPut) {
        Work work = workRepo.getWorkByIdEquals(id);

        if (workToPut.getStudentComment() == null && (workToPut.getUserFiles() == null || workToPut.getUserFiles().isEmpty())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Нельзя сделать работу без файлов и комментариев");
        }

        Task task = workToPut.getTask();
        Set<UserFile> userFiles = workToPut.getUserFiles();

        work.getUserFiles().forEach(UserFile::decLinkCount);
        userFiles.forEach(UserFile::incLinkCount);

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

    public Collection<Work> searchByIds(Set<Long> ids) {
        return workRepo.getWorksByIdIn(ids);
    }

    public Collection<Work> searchByGroupsAndTeacher(Integer userId, Long groupId, UserDetailsImp userDetails) {
        if (userId == null) {
            userId = userDetails.getId();
        }

        User user = userRepo.getOne(userId);
        if (user.getRole() != UserRole.TEACHER) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Пользователь не является преподавателем");
        }

        Optional<StudyGroup> groupO = studyGroupRepo.findById(groupId);
        if (groupO.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Группа с указанным id не существует");
        }
        StudyGroup group = groupO.get();

        return group
                .getSubjectSemesters().stream()
                .filter(sem -> sem.getSubject().getTeachers().contains(user))
                .flatMap(sem -> sem.getTasks().stream())
                .flatMap(t -> t.getWorks().stream())
                .collect(Collectors.toSet());
    }
}
