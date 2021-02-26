package com.example.javaserver.common_data.service;

import com.example.javaserver.common_data.model.StudyGroup;
import com.example.javaserver.common_data.model.Subject;
import com.example.javaserver.common_data.model.SubjectSemester;
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

    @Autowired
    public SubjectService(SubjectRepo subjectRepo, UserRepo userRepo) {
        this.subjectRepo = subjectRepo;
        this.userRepo = userRepo;
    }

    @Transactional
    public ResponseEntity<?> create(Subject subject) {
        subjectRepo.save(subject);
        return new ResponseEntity<>(new Message("Предмет успешно создан"), HttpStatus.OK);
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
