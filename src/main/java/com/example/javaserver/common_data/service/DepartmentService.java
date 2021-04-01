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
import org.springframework.web.server.ResponseStatusException;

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
    public Message create(DepartmentIn departmentIn) {
        Department department = new Department();
        department.setFullName(departmentIn.fullName);
        department.setShortName(departmentIn.shortName);

        if (departmentIn.facultyId != null) {
            Optional<Faculty> faculty = facultyRepo.findById(departmentIn.facultyId);
            if (!faculty.isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Факультет с указанным id не существует");
            }
            department.setFaculty(faculty.get());
        }

        departmentRepo.save(department);
        return new Message("Кафедра успешно создана");
    }

    public Message delete(Set<Long> ids) {
        departmentRepo.deleteAllByIdIn(ids);
        return new Message("Найденные кафедры были успешно удалены");
    }

    @SuppressWarnings("Duplicates")
    @Transactional
    public Message update(
            Long id,
            String shortName,
            String fullName,
            String facultyId
    ) {
        Optional<Department> departmentOptional = departmentRepo.findById(id);
        if (!departmentOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Кафедра с указанным id не существует");
        }
        Department department = departmentOptional.get();

        if (shortName != null) {
            try {
                department.setShortName(shortName.equals("null") ? null : shortName);
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Недопустимое значение поля: shortName");
            }
        }

        if (fullName != null) {
            try {
                department.setFullName(fullName.equals("null") ? null : fullName);
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Недопустимое значение поля: fullName");
            }
        }

        if (facultyId != null) {
            Faculty faculty = null;
            if (!facultyId.equals("null")) {
                Optional<Faculty> facultyOptional;
                try {
                    facultyOptional = facultyRepo.findById(Long.parseLong(facultyId));
                } catch (Exception e) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ошибка изменения кафедры. Недопустимый id факультета");
                }
                if (!facultyOptional.isPresent()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ошибка изменения кафедры. Факультет с указанным id не существует");
                }
                faculty = facultyOptional.get();
            }
            department.setFaculty(faculty);
        }

        return new Message("Кафедра была успешно изменена");
    }

    public Collection<Department> getAll() {
        return departmentRepo.findAllBy();
    }

    public Collection<String> getAllShortNames() {
        return departmentRepo
                .findAllBy()
                .stream()
                .map(Department::getShortName)
                .collect(Collectors.toSet());
    }

    public Collection<Department>  criteriaSearch(Set<SearchCriteria> criteria) {
        try {
            Specification<Department> specification = CommonSpecification.of(criteria);
            return departmentRepo.findAll(specification);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Критерии поиска некорректны");
        }
    }

    public Collection<Department> searchByIds(Set<Long> ids) {
        return departmentRepo.findAllByIdIn(ids);
    }
}
