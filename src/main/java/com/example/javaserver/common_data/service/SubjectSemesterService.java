package com.example.javaserver.common_data.service;

import com.example.javaserver.common_data.model.Subject;
import com.example.javaserver.common_data.model.SubjectSemester;
import com.example.javaserver.common_data.repo.SubjectRepo;
import com.example.javaserver.common_data.repo.SubjectSemesterRepo;
import com.example.javaserver.general.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class SubjectSemesterService {
    private final SubjectSemesterRepo subjectSemesterRepo;
    private final SubjectRepo subjectRepo;

    @Autowired
    public SubjectSemesterService(SubjectSemesterRepo subjectSemesterRepo, SubjectRepo subjectRepo) {
        this.subjectSemesterRepo = subjectSemesterRepo;
        this.subjectRepo = subjectRepo;
    }

    @Transactional
    public ResponseEntity<?> create(SubjectSemester subjectSemester) {
        subjectSemesterRepo.save(subjectSemester);
        return new ResponseEntity<>(new Message("Семестр предмета успешно создан"), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> setSubject(Long subjectSemesterId, Long subjectId) {
        Optional<SubjectSemester> subjectSemester = subjectSemesterRepo.findById(subjectSemesterId);
        if (!subjectSemester.isPresent()) {
            return new ResponseEntity<>(new Message("Семестр с указанным id не был найден"), HttpStatus.BAD_REQUEST);
        }

        Optional<Subject> subject = subjectRepo.findById(subjectId);
        if (!subject.isPresent()) {
            return new ResponseEntity<>(new Message("Предмет с указанным id не был найден"), HttpStatus.BAD_REQUEST);
        }

        subjectSemester.get().setSubject(subject.get());
        return new ResponseEntity<>(new Message("Прелмет был успешно привязан к семестру"), HttpStatus.OK);
    }
}
