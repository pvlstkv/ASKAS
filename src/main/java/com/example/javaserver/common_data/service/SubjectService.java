package com.example.javaserver.common_data.service;

import com.example.javaserver.common_data.model.Department;
import com.example.javaserver.common_data.model.StudyGroup;
import com.example.javaserver.common_data.model.Subject;
import com.example.javaserver.common_data.model.SubjectSemester;
import com.example.javaserver.common_data.repo.DepartmentRepo;
import com.example.javaserver.general.criteria.SearchCriteria;
import com.example.javaserver.general.model.Message;
import com.example.javaserver.general.model.UserDetailsImp;
import com.example.javaserver.general.specification.CommonSpecification;
import com.example.javaserver.user.model.User;
import com.example.javaserver.user.model.UserRole;
import com.example.javaserver.user.service.UserDataService;
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

@Service
public class SubjectService {
    private final SubjectDataService subjectDataService;
    private final UserDataService userDataService;
    private final DepartmentRepo departmentRepo;

    @Autowired
    public SubjectService(SubjectDataService subjectDataService, UserDataService userDataService, DepartmentRepo departmentRepo) {
        this.subjectDataService = subjectDataService;
        this.userDataService = userDataService;
        this.departmentRepo = departmentRepo;
    }

    @Transactional
    public Subject create(Subject subject) {
        return subjectDataService.save(subject);
    }

    @Transactional
    public Message delete(Set<Long> ids) {
        subjectDataService.deleteAllByIdIn(ids);
        return new Message("Найденные предметы были успешно удалены");
    }

    @SuppressWarnings("Duplicates")
    @Transactional
    public Message update(
            Long id,
            String name,
            String decryption,
            String departmentId
    ) {
        Subject subject = subjectDataService.getById(id);
        if (name != null) {
            try {
                subject.setName(name.equals("null") ? null : name);
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Недопустимое значение поля: name");
            }
        }
        if (decryption != null) {
            try {
                subject.setDecryption(decryption.equals("null") ? null : decryption);
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Недопустимое значение поля: decryption");
            }
        }
        if (departmentId != null) {
            Department department = null;
            if (!departmentId.equals("null")) {
                Optional<Department> departmentOptional;
                try {
                    departmentOptional = departmentRepo.findById(Long.parseLong(departmentId));
                } catch (Exception e) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ошибка изменения предмета. Недопустимый id кафедры");
                }
                if (departmentOptional.isEmpty()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ошибка изменения предмета. Кафедра с указанным id не существует");
                }
                department = departmentOptional.get();
            }
            subject.setDepartment(department);
        }
        return new Message("Предмет был успешно изменён");
    }

    public Collection<Subject> getAll() {
        return subjectDataService.findAllBy();
    }

    public Collection<Subject> criteriaSearch(Set<SearchCriteria> criteria) {
        try {
            Specification<Subject> specification = CommonSpecification.of(criteria);
            return subjectDataService.findAll(specification);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Критерии поиска некорректны");
        }
    }

    public Subject getById(Long id) {
        Subject subject = subjectDataService.getById(id);
        return subject;
    }

    public Set<Subject> getByIds(Set<Long> ids) {
        Set<Subject> subjects = subjectDataService.findAllByIdIn(ids);
        if (subjects.size() == ids.size()) {
            return subjects;
        } else {
            Collection<Long> foundIds = subjects.stream()
                    .map(Subject::getId)
                    .collect(Collectors.toSet());
            Collection<Long> notFoundIds = ids.stream()
                    .filter(i -> !foundIds.contains(i))
                    .collect(Collectors.toSet());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Предметы с id: " + Arrays.toString(notFoundIds.toArray()) + " не существуют");
        }
    }

    public Collection<Subject> searchByStudentId(Integer userId, UserDetailsImp userDetails) {
        if (userId == null) {
            userId = userDetails.getId();
        }

        User user = userDataService.getById(userId);

        StudyGroup group = user.getStudyGroup();
        if (group == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Пользователь не привязан ни к какой группе");
        }

        return group
                .getSubjectSemesters()
                .stream()
                .map(SubjectSemester::getSubject)
                .collect(Collectors.toSet());
    }

    public Collection<Subject> searchByTeacherId(Integer userId, UserDetailsImp userDetails) {
        if (userId == null) {
            userId = userDetails.getId();
        }
        User user = userDataService.getById(userId);
        if (!user.getRole().equals(UserRole.TEACHER)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Пользователь не является преподавателем");
        }
        return user.getTeachingSubjects();
    }

    @Transactional
    public Message addTeachers(
           Long subjectId,
           Set<Integer> userIds
    ){
        Subject subject = subjectDataService.getById(subjectId);
        Set<User> userSet = userDataService.getUsersByIdInAndRoleEquals(userIds,UserRole.TEACHER);
        subject.getTeachers().addAll(userSet);
        subjectDataService.save(subject);
        return new Message("преподаватели добавлены");
    }
}
