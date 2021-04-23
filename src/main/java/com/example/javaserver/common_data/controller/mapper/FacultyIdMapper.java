package com.example.javaserver.common_data.controller.mapper;

import com.example.javaserver.common_data.model.Department;
import com.example.javaserver.common_data.model.Faculty;
import com.example.javaserver.common_data.service.DepartmentDataService;
import com.example.javaserver.common_data.service.FacultyDataService;
import com.example.javaserver.study.model.Literature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
@Component
public class FacultyIdMapper {
    private final FacultyDataService service;

    @Autowired
    public FacultyIdMapper(FacultyDataService service) {
        this.service = service;
    }

    public Faculty getById(Long id) {
        return id == null
                ? null
                : service.getById(id);
    }

    public Long getId(Faculty faculty) {
        return faculty == null
                ? null
                : faculty.getId();
    }

    public Set<Faculty> getByIds(Set<Long> ids) {
        return ids == null
                ? null
                : service.getByIds(ids);
    }

    public Set<Long> getIds(Set<Faculty> faculties) {
        return faculties == null
                ? null
                : faculties.stream().map(Faculty::getId).collect(Collectors.toSet());
    }
}
