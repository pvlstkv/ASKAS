package com.example.javaserver.common_data.service;

import com.example.javaserver.common_data.controller.client_model.SubjectIn;
import com.example.javaserver.common_data.model.Department;
import com.example.javaserver.common_data.model.StudyGroup;
import com.example.javaserver.common_data.model.Subject;
import com.example.javaserver.common_data.model.SubjectSemester;
import com.example.javaserver.common_data.repo.DepartmentRepo;
import com.example.javaserver.common_data.repo.SubjectRepo;
import com.example.javaserver.general.criteria.SearchCriteria;
import com.example.javaserver.general.model.Message;
import com.example.javaserver.general.model.UserDetailsImp;
import com.example.javaserver.general.specification.CommonSpecification;
import com.example.javaserver.user.model.User;
import com.example.javaserver.user.model.UserRole;
import com.example.javaserver.user.repo.UserRepo;
import jdk.internal.loader.AbstractClassLoaderValue;
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

@Service
public class SubjectService {
    private final SubjectRepo subjectRepo;
    private final UserRepo userRepo;
    private final DepartmentRepo departmentRepo;

    @Autowired
    public SubjectService(SubjectRepo subjectRepo, UserRepo userRepo, DepartmentRepo departmentRepo) {
        this.subjectRepo = subjectRepo;
        this.userRepo = userRepo;
        this.departmentRepo = departmentRepo;
    }

    @Transactional
    public Message create(SubjectIn subjectIn) {
        Subject subject = new Subject();
        subject.setName(subjectIn.name);
        subject.setDecryption(subjectIn.decryption);

        if (subjectIn.departmentId != null) {
            Optional<Department> department = departmentRepo.findById(subjectIn.departmentId);
            if (!department.isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Кафедра с указанным id не существует");
            }
            subject.setDepartment(department.get());
        }

        subjectRepo.save(subject);
        return new Message("Предмет успешно создан");
    }

    public Message delete(Set<Long> ids) {
        subjectRepo.deleteAllByIdIn(ids);
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
        Optional<Subject> subjectOptional = subjectRepo.findById(id);
        if (!subjectOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Предмет с указанным id не существует");
        }
        Subject subject = subjectOptional.get();

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
                if (!departmentOptional.isPresent()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ошибка изменения предмета. Кафедра с указанным id не существует");
                }
                department = departmentOptional.get();
            }
            subject.setDepartment(department);
        }

        return new Message("Предмет был успешно изменён");
    }

    public Collection<Subject> getAll() {
        return subjectRepo.findAllBy();
    }

    public Collection<Subject> criteriaSearch(Set<SearchCriteria> criteria) {
        try {
            Specification<Subject> specification = CommonSpecification.of(criteria);
            return subjectRepo.findAll(specification);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Критерии поиска некорректны");
        }
    }

    public Collection<Subject> searchByIds(Set<Long> ids) {
        return subjectRepo.findAllByIdIn(ids);
    }

    public Collection<Subject> searchByStudentId(Integer userId, UserDetailsImp userDetails) {
        if (userId == null) {
            userId = userDetails.getId();
        }

        Optional<User> user = userRepo.findById(userId);
        if (!user.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Пользователь с указанным id не найден");
        }

        StudyGroup group = user.get().getStudyGroup();
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

        Optional<User> user = userRepo.findById(userId);
        if (!user.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Пользователь с указанным id не найден");
        }

        if (!user.get().getRole().equals(UserRole.TEACHER)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Пользователь не является преподавателем");
        }

        return user.get().getTeachingSubjects();
    }

    @Transactional
    public Message addTeachers(
           Long subjectId,
           Set<Integer> userIds
    ){
        Optional<Subject> subject = subjectRepo.findById(subjectId);
        if(!subject.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "нет такого предмета");
        }
        Set<User> userSet = userRepo.getUsersByIdInAndRoleEquals(userIds,UserRole.TEACHER);
        subject.get().getTeachers().addAll(userSet);

        subjectRepo.save(subject.get());
        return new Message("преподаватели добавлены");
    }
}
