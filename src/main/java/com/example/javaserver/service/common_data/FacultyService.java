package com.example.javaserver.service.common_data;

import com.example.javaserver.basemodel.Message;
import com.example.javaserver.model.common_data.Faculty;
import com.example.javaserver.repo.FacultyRepo;
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
    public ResponseEntity<?> createFaculty(Faculty faculty) {
        facultyRepo.save(faculty);
        return new ResponseEntity<>(new Message("Факультет успешно создан"), HttpStatus.OK);
    }
}
