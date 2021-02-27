package com.example.javaserver.common_data.service;

import com.example.javaserver.common_data.controller.client_model.FacultyIO;
import com.example.javaserver.common_data.model.Faculty;
import com.example.javaserver.common_data.repo.FacultyRepo;
import com.example.javaserver.common_data.repo.specification.FacultySpecification;
import com.example.javaserver.common_data.repo.specification.SearchCriteria;
import com.example.javaserver.general.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Service
public class FacultyService {
    private final FacultyRepo facultyRepo;

    @Autowired
    public FacultyService(FacultyRepo facultyRepo) {
        this.facultyRepo = facultyRepo;
    }

    @Transactional
    public ResponseEntity<?> create(FacultyIO facultyIO) {
        Faculty faculty = new Faculty();
        faculty.setShortName(facultyIO.short_name);
        faculty.setFullName(facultyIO.full_name);

        facultyRepo.save(faculty);
        return new ResponseEntity<>(new Message("Факультет успешно создан"), HttpStatus.OK);
    }

    public ResponseEntity<?> search(Set<SearchCriteria> criteria) {
        Iterator<SearchCriteria> it = criteria.iterator();
        List<Faculty> faculties = new ArrayList<>();
        if (it.hasNext()) {
            Specification<Faculty> specification = new FacultySpecification(it.next());
            while (it.hasNext()) {
                specification = specification.and(new FacultySpecification(it.next()));
            }
            faculties = facultyRepo.findAll(specification);
        }
        return new ResponseEntity<>(faculties, HttpStatus.OK);
    }
}
