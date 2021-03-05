package com.example.javaserver.common_data.service;

import com.example.javaserver.common_data.controller.client_model.FacultyIn;
import com.example.javaserver.common_data.model.Faculty;
import com.example.javaserver.common_data.repo.FacultyRepo;
import com.example.javaserver.general.specification.CommonSpecification;
import com.example.javaserver.general.criteria.SearchCriteria;
import com.example.javaserver.general.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

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

    @Transactional
    public ResponseEntity<?> delete(Set<Long> ids) {
        facultyRepo.deleteAllByIdIn(ids);
        return new ResponseEntity<>(new Message("Найденные факультеты были успешно удалены"), HttpStatus.OK);
    }

    @SuppressWarnings("Duplicates")
    @Transactional
    public ResponseEntity<?> update(
            Long id,
            String shortName,
            String fullName
    ) {
        Optional<Faculty> facultyOptional = facultyRepo.findById(id);
        if (!facultyOptional.isPresent()) {
            return new ResponseEntity<>(new Message("Факультет с указанным id не существует"), HttpStatus.BAD_REQUEST);
        }
        Faculty faculty = facultyOptional.get();

        if (shortName != null) {
            try {
                faculty.setShortName(shortName.equals("null") ? null : shortName);
            } catch (Exception e) {
                return new ResponseEntity<>(new Message("Недопустимое значение поля: shortName"), HttpStatus.BAD_REQUEST);
            }
        }

        if (fullName != null) {
            try {
                faculty.setFullName(fullName.equals("null") ? null : fullName);
            } catch (Exception e) {
                return new ResponseEntity<>(new Message("Недопустимое значение поля: fullName"), HttpStatus.BAD_REQUEST);
            }
        }

        return new ResponseEntity<>(new Message("Факультет был успешно изменён"), HttpStatus.OK);
    }

    public ResponseEntity<?> getAll() {
        Collection<Faculty> faculties = facultyRepo.findAllBy();
        return new ResponseEntity<>(faculties, HttpStatus.OK);
    }

    public ResponseEntity<?> criteriaSearch(Set<SearchCriteria> criteria) {
        try {
            Specification<Faculty> specification = CommonSpecification.of(criteria);
            List<Faculty> faculties = facultyRepo.findAll(specification);
            return new ResponseEntity<>(faculties, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new Message("Критерии поиска некорректны"), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> searchByIds(Set<Long> ids) {
        Collection<Faculty> faculties = facultyRepo.findAllByIdIn(ids);
        return new ResponseEntity<>(faculties, HttpStatus.OK);
    }
}
