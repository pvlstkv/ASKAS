package com.example.javaserver.common_data.service;

import com.example.javaserver.common_data.controller.client_model.DepartmentIn;
import com.example.javaserver.common_data.model.Department;
import com.example.javaserver.common_data.model.Faculty;
import com.example.javaserver.common_data.repo.DepartmentRepo;
import com.example.javaserver.common_data.repo.FacultyRepo;
import com.example.javaserver.general.criteria.SearchCriteria;
import com.example.javaserver.general.model.Message;
import com.example.javaserver.general.specification.CommonSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class DepartmentService {
    private final DepartmentRepo departmentRepo;
    private final FacultyRepo facultyRepo;

    @Autowired
    public DepartmentService(DepartmentRepo departmentRepo, FacultyRepo facultyRepo) {
        this.departmentRepo = departmentRepo;
        this.facultyRepo = facultyRepo;
    }

    @Transactional
    public ResponseEntity<?> create(DepartmentIn departmentIn) {
        Department department = new Department();
        department.setFullName(departmentIn.fullName);
        department.setShortName(departmentIn.shortName);

        if (departmentIn.facultyId != null) {
            Optional<Faculty> faculty = facultyRepo.findById(departmentIn.facultyId);
            if (!faculty.isPresent()) {
                return new ResponseEntity<>(new Message("Факультет с указанным id не существует"), HttpStatus.BAD_REQUEST);
            }
            department.setFaculty(faculty.get());
        }

        departmentRepo.save(department);
        return new ResponseEntity<>(new Message("Кафедра успешно создана"), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> delete(Set<Long> ids) {
        departmentRepo.deleteAllByIdIn(ids);
        return new ResponseEntity<>(new Message("Найденные кафедры были успешно удалены"), HttpStatus.OK);
    }

    public ResponseEntity<?> search(Set<SearchCriteria> criteria) {
        try {
            Specification<Department> specification = CommonSpecification.of(criteria);
            List<Department> departments = departmentRepo.findAll(specification);
            return new ResponseEntity<>(departments, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new Message("Критерии поиска некорректны"), HttpStatus.BAD_REQUEST);
        }
    }
}
