package com.example.javaserver.common_data.service;

import com.example.javaserver.common_data.model.Subject;
import com.example.javaserver.common_data.repo.SubjectRepo;
import com.example.javaserver.general.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class SubjectService {
    private final SubjectRepo subjectRepo;

    @Autowired
    public SubjectService(SubjectRepo subjectRepo) {
        this.subjectRepo = subjectRepo;
    }

    @Transactional
    public ResponseEntity<?> create(Subject subject) {
        subjectRepo.save(subject);
        return new ResponseEntity<>(new Message("Предмет успешно создан"), HttpStatus.OK);
    }
}
