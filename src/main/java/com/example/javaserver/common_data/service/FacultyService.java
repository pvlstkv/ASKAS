package com.example.javaserver.common_data.service;

import com.example.javaserver.general.model.Message;
import com.example.javaserver.common_data.model.Faculty;
import com.example.javaserver.common_data.repo.FacultyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class FacultyService {
    private final FacultyRepo facultyRepo;

    @Autowired
    public FacultyService(FacultyRepo facultyRepo) {
        this.facultyRepo = facultyRepo;
    }

    @Transactional
    public ResponseEntity<?> create(Faculty faculty) {
        facultyRepo.save(faculty);
        return new ResponseEntity<>(new Message("Факультет успешно создан"), HttpStatus.OK);
    }
}
