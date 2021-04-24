package com.example.javaserver.common_data.controller;

import com.example.javaserver.common_data.controller.dto.DepartmentDto;
import com.example.javaserver.common_data.controller.mapper.DepartamentMapper;
import com.example.javaserver.common_data.model.Department;
import com.example.javaserver.common_data.service.DepartmentService;
import com.example.javaserver.general.criteria.SearchCriteria;
import com.example.javaserver.general.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Set;

@RestController
@RequestMapping("/department")
public class DepartmentController {
    private final DepartmentService departmentService;
    private final DepartamentMapper departamentMapper;

    @Autowired
    public DepartmentController(DepartmentService departmentService, DepartamentMapper departamentMapper) {
        this.departmentService = departmentService;
        this.departamentMapper = departamentMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Secured({"ADMIN"})
    public DepartmentDto create(
            @Valid @RequestBody DepartmentDto departmentDto
    ) {
        return departamentMapper.toDto(
                departmentService.create(
                        departamentMapper.toEntity(departmentDto)
                )
        );

    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ADMIN"})
    public Message delete(
            @RequestBody Set<Long> ids
    ) {
        return departmentService.delete(ids);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ADMIN"})
    public Message update(
            @RequestParam("id") Long id,
            @RequestParam(value = "shortName", required = false) String shortName,
            @RequestParam(value = "fullName", required = false) String fullName,
            @RequestParam(value = "facultyId", required = false) String facultyId
    ) {
        return departmentService.update(id, shortName, fullName, facultyId);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    public Collection<DepartmentDto> search() {
        return departamentMapper.toDto(
                departmentService.getAll()
        );
    }

    @GetMapping("/all/short-names")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    public Collection<String> searchAllShortNames() {
        return departmentService.getAllShortNames();
    }

    @PostMapping("/criteria-search")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    public Collection<DepartmentDto> criteriaSearch(
            @RequestBody Set<SearchCriteria> criteria
    ) {
        return departamentMapper.toDto(
                departmentService.criteriaSearch(criteria)
        );
    }

    @GetMapping("/search-by-ids")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    public Collection<DepartmentDto> searchByIds(
            @RequestParam("ids") Set<Long> ids
    ) {
        return departamentMapper.toDto(
                departmentService.searchByIds(ids)
        );
    }
}
