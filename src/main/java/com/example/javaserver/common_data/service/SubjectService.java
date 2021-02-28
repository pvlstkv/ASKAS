package com.example.javaserver.common_data.service;

import com.example.javaserver.common_data.controller.client_model.SubjectIn;
import com.example.javaserver.common_data.model.*;
import com.example.javaserver.common_data.repo.DepartmentRepo;
import com.example.javaserver.common_data.repo.SubjectRepo;
import com.example.javaserver.general.model.Message;
import com.example.javaserver.user.model.User;
import com.example.javaserver.user.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
        subjectRepo.deleteAllByIdIn(
                ids.stream().map(Number::intValue).collect(Collectors.toSet()) // todo эту херню убрать
        );
        return new ResponseEntity<>(new Message("Найденные предметы были успешно удалены"), HttpStatus.OK);
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

}
