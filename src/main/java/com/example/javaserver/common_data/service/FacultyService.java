package com.example.javaserver.common_data.service;

import com.example.javaserver.common_data.controller.client_model.FacultyIn;
import com.example.javaserver.common_data.model.Faculty;
import com.example.javaserver.common_data.repo.FacultyRepo;
import com.example.javaserver.general.specification.CommonSpecification;
import com.example.javaserver.general.specification.SearchCriteria;
import com.example.javaserver.general.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class FacultyService {
    private final FacultyRepo facultyRepo;

    @Autowired
    public FacultyService(FacultyRepo facultyRepo) {
        this.facultyRepo = facultyRepo;
    }

    @Transactional
    public ResponseEntity<?> create(FacultyIn facultyIn) {
        Faculty faculty = new Faculty();
        faculty.setShortName(facultyIn.shortName);
        faculty.setFullName(facultyIn.fullName);

        facultyRepo.save(faculty);
        return new ResponseEntity<>(new Message("Факультет успешно создан"), HttpStatus.OK);
    }

    public ResponseEntity<?> search(Collection<SearchCriteria> criteria) {
        try {
            Specification<Faculty> specification = CommonSpecification.of(criteria);
            List<Faculty> faculties = facultyRepo.findAll(specification);
            return new ResponseEntity<>(faculties, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new Message("Критерии поиска некорректны"), HttpStatus.BAD_REQUEST);
        }
    }
}
