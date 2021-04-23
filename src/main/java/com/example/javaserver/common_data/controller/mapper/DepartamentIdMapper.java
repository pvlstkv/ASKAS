package com.example.javaserver.common_data.controller.mapper;

import com.example.javaserver.common_data.model.Department;
import com.example.javaserver.common_data.service.DepartmentDataService;
import com.example.javaserver.study.model.Literature;
import com.example.javaserver.study.service.LiteratureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
@Component
public class DepartamentIdMapper {

    private final DepartmentDataService service;

    @Autowired
    public DepartamentIdMapper(DepartmentDataService service) {
        this.service = service;
    }

    public Department getById(Long id) {
        return id == null
                ? null
                : service.getById(id);
    }

    public Long getId(Department department) {
        return department == null
                ? null
                : department.getId();
    }

    public Set<Department> getByIds(Set<Long> ids) {
        return ids == null
                ? null
                : service.getByIds(ids);
    }

    public Set<Long> getIds(Set<Department> departments) {
        return departments == null
                ? null
                : departments.stream().map(Department::getId).collect(Collectors.toSet());
    }
}
