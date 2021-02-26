package com.example.javaserver.common_data.service;

import com.example.javaserver.common_data.model.SubjectSemester;
import com.example.javaserver.common_data.repo.SubjectSemesterRepo;
import com.example.javaserver.general.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class SubjectSemesterService {
    private final SubjectSemesterRepo subjectSemesterRepo;

    @Autowired
    public SubjectSemesterService(SubjectSemesterRepo subjectSemesterRepo) {
        this.subjectSemesterRepo = subjectSemesterRepo;
    }

    @Transactional
    public ResponseEntity<?> create(SubjectSemester subjectSemester) {
        subjectSemesterRepo.save(subjectSemester);
        return new ResponseEntity<>(new Message("Семестр предмета успешно создан"), HttpStatus.OK);
    }
}
