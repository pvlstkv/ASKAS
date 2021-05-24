package com.example.javaserver.common_data.service;

import com.example.javaserver.common_data.model.Department;
import com.example.javaserver.common_data.model.Faculty;
import com.example.javaserver.general.criteria.SearchCriteria;
import com.example.javaserver.general.model.Message;
import com.example.javaserver.general.specification.CommonSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Set;

@Service
public class DepartmentService {

    private final DepartmentDataService departmentDataService;
    private final FacultyDataService facultyDataService;

    @Autowired
    public DepartmentService(DepartmentDataService departmentDataService, FacultyDataService facultyDataService) {
        this.departmentDataService = departmentDataService;
        this.facultyDataService = facultyDataService;
    }

    @Transactional
    public Department create(Department department) {
        return departmentDataService.save(department);
    }

    public Message delete(Set<Long> ids) {
        departmentDataService.deleteAllByIdIn(ids);
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
        Department department = departmentDataService.getById(id);
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
                try {
                    faculty = facultyDataService.getById(Long.parseLong(facultyId));
                } catch (Exception e) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ошибка изменения кафедры. Недопустимый id факультета");
                }
            }
            department.setFaculty(faculty);
        }

        return new Message("Кафедра была успешно изменена");
    }

    public Collection<Department> getAll() {
        return departmentDataService.findAll();
    }

    public Collection<String> getAllShortNames() {
       return departmentDataService.getAllShortNames();
    }

    public Collection<Department>  criteriaSearch(Set<SearchCriteria> criteria) {
        try {
            Specification<Department> specification = CommonSpecification.of(criteria);
            return departmentDataService.findAll(specification);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Критерии поиска некорректны");
        }
    }

    public Collection<Department> searchByIds(Set<Long> ids) {
        return departmentDataService.findAllByIdIn(ids);
    }
}
