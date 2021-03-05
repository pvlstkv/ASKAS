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
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

    @SuppressWarnings("Duplicates")
    @Transactional
    public ResponseEntity<?> update(
            Long id,
            String shortName,
            String fullName,
            String facultyId
    ) {
        Optional<Department> departmentOptional = departmentRepo.findById(id);
        if (!departmentOptional.isPresent()) {
            return new ResponseEntity<>(new Message("Кафедра с указанным id не существует"), HttpStatus.BAD_REQUEST);
        }
        Department department = departmentOptional.get();

        if (shortName != null) {
            try {
                department.setShortName(shortName.equals("null") ? null : shortName);
            } catch (Exception e) {
                return new ResponseEntity<>(new Message("Недопустимое значение поля: shortName"), HttpStatus.BAD_REQUEST);
            }
        }

        if (fullName != null) {
            try {
                department.setFullName(fullName.equals("null") ? null : fullName);
            } catch (Exception e) {
                return new ResponseEntity<>(new Message("Недопустимое значение поля: fullName"), HttpStatus.BAD_REQUEST);
            }
        }

        if (facultyId != null) {
            Faculty faculty = null;
            if (!facultyId.equals("null")) {
                Optional<Faculty> facultyOptional;
                try {
                    facultyOptional = facultyRepo.findById(Long.parseLong(facultyId));
                } catch (Exception e) {
                    return new ResponseEntity<>(new Message("Ошибка изменения кафедры. Недопустимый id факультета"), HttpStatus.BAD_REQUEST);
                }
                if (!facultyOptional.isPresent()) {
                    return new ResponseEntity<>(new Message("Ошибка изменения кафедры. Факультет с указанным id не существует"), HttpStatus.BAD_REQUEST);
                }
                faculty = facultyOptional.get();
            }
            department.setFaculty(faculty);
        }

        return new ResponseEntity<>(new Message("Кафедра была успешно изменена"), HttpStatus.OK);
    }

    public ResponseEntity<?> getAll() {
        Collection<Department> departments = departmentRepo.findAllBy();
        return new ResponseEntity<>(departments, HttpStatus.OK);
    }

    public ResponseEntity<?> getAllShortNames() {
        Collection<String> shortNames = departmentRepo
                .findAllBy()
                .stream()
                .map(Department::getShortName)
                .collect(Collectors.toSet());
        return new ResponseEntity<>(shortNames, HttpStatus.OK);
    }

    public ResponseEntity<?> criteriaSearch(Set<SearchCriteria> criteria) {
        try {
            Specification<Department> specification = CommonSpecification.of(criteria);
            List<Department> departments = departmentRepo.findAll(specification);
            return new ResponseEntity<>(departments, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new Message("Критерии поиска некорректны"), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> searchByIds(Set<Long> ids) {
        Collection<Department> departments = departmentRepo.findAllByIdIn(ids);
        return new ResponseEntity<>(departments, HttpStatus.OK);
    }
}
