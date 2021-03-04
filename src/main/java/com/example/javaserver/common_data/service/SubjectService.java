package com.example.javaserver.common_data.service;

import com.example.javaserver.common_data.controller.client_model.SubjectIn;
import com.example.javaserver.common_data.model.*;
import com.example.javaserver.common_data.repo.DepartmentRepo;
import com.example.javaserver.common_data.repo.SubjectRepo;
import com.example.javaserver.general.criteria.SearchCriteria;
import com.example.javaserver.general.model.Message;
import com.example.javaserver.general.specification.CommonSpecification;
import com.example.javaserver.user.model.User;
import com.example.javaserver.user.model.UserRole;
import com.example.javaserver.user.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
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
    public ResponseEntity<?> create(SubjectIn subjectIn) {
        Subject subject = new Subject();
        subject.setName(subjectIn.name);
        subject.setDecryption(subjectIn.decryption);

        if (subjectIn.departmentId != null) {
            Optional<Department> department = departmentRepo.findById(subjectIn.departmentId);
            if (!department.isPresent()) {
                return new ResponseEntity<>(new Message("Кафедра с указанным id не существует"), HttpStatus.BAD_REQUEST);
            }
            subject.setDepartment(department.get());
        }

        subjectRepo.save(subject);
        return new ResponseEntity<>(new Message("Предмет успешно создан"), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> delete(Set<Long> ids) {
        subjectRepo.deleteAllByIdIn(ids);
        return new ResponseEntity<>(new Message("Найденные предметы были успешно удалены"), HttpStatus.OK);
    }

    @SuppressWarnings("Duplicates")
    @Transactional
    public ResponseEntity<?> update(
            Long id,
            String name,
            String decryption,
            String departmentId
    ) {
        Optional<Subject> subjectOptional = subjectRepo.findById(id);
        if (!subjectOptional.isPresent()) {
            return new ResponseEntity<>(new Message("Предмет с указанным id не существует"), HttpStatus.BAD_REQUEST);
        }
        Subject subject = subjectOptional.get();

        if (name != null) {
            try {
                subject.setName(name.equals("null") ? null : name);
            } catch (Exception e) {
                return new ResponseEntity<>(new Message("Недопустимое значение поля: name"), HttpStatus.BAD_REQUEST);
            }
        }

        if (decryption != null) {
            try {
                subject.setDecryption(decryption.equals("null") ? null : decryption);
            } catch (Exception e) {
                return new ResponseEntity<>(new Message("Недопустимое значение поля: decryption"), HttpStatus.BAD_REQUEST);
            }
        }

        if (departmentId != null) {
            Department department = null;
            if (!departmentId.equals("null")) {
                Optional<Department> departmentOptional;
                try {
                    departmentOptional = departmentRepo.findById(Long.parseLong(departmentId));
                } catch (Exception e) {
                    return new ResponseEntity<>(new Message("Ошибка изменения предмета. Недопустимый id кафедры"), HttpStatus.BAD_REQUEST);
                }
                if (!departmentOptional.isPresent()) {
                    return new ResponseEntity<>(new Message("Ошибка изменения предмета. Кафедра с указанным id не существует"), HttpStatus.BAD_REQUEST);
                }
                department = departmentOptional.get();
            }
            subject.setDepartment(department);
        }

        return new ResponseEntity<>(new Message("Предмет был успешно изменён"), HttpStatus.OK);
    }

    public ResponseEntity<?> getAll() {
        Collection<Subject> subjects = subjectRepo.findAllBy();
        return new ResponseEntity<>(subjects, HttpStatus.OK);
    }

    public ResponseEntity<?> criteriaSearch(Set<SearchCriteria> criteria) {
        try {
            Specification<Subject> specification = CommonSpecification.of(criteria);
            List<Subject> subjects = subjectRepo.findAll(specification);
            return new ResponseEntity<>(subjects, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new Message("Критерии поиска некорректны"), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> searchByIds(Set<Long> ids) {
        Collection<Subject> subjects = subjectRepo.findAllByIdIn(ids);
        return new ResponseEntity<>(subjects, HttpStatus.OK);
    }

    public ResponseEntity<?> searchByUserId(Integer userId) {
        Optional<User> user = userRepo.findById(userId);
        if (!user.isPresent()) {
            return new ResponseEntity<>(new Message("Пользователь с указанным id не найден"), HttpStatus.BAD_REQUEST);
        }

        StudyGroup group = user.get().getStudyGroup();
        if (group == null) {
            return new ResponseEntity<>(new Message("Пользователь не привязан ни к какой группе"), HttpStatus.BAD_REQUEST);
        }

        Set<Subject> subjects = group
                .getSubjectSemesters()
                .stream()
                .map(SubjectSemester::getSubject)
                .collect(Collectors.toSet());
        return new ResponseEntity<>(subjects, HttpStatus.OK);
    }

    public ResponseEntity<?> addTeachers(
           Long subjectId,
           Set<Integer> userIds
    ){
        Optional<Subject> subject = subjectRepo.findById(subjectId);
        if(!subject.isPresent()){
            return new ResponseEntity<>(new Message("нет такого предмета"), HttpStatus.BAD_REQUEST);
        }
        Set<User> userSet = userRepo.getUsersByIdInAndRoleEquals(userIds,UserRole.TEACHER);
        subject.get().getTeachers().addAll(userSet);

        subjectRepo.save(subject.get());
        return new ResponseEntity<>(new Message("преподаватели добавлены"), HttpStatus.OK);

    }
}
