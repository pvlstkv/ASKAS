package com.example.javaserver.common_data.controller;

import com.example.javaserver.common_data.controller.client_model.DepartmentIn;
import com.example.javaserver.common_data.model.Department;
import com.example.javaserver.common_data.service.DepartmentService;
import com.example.javaserver.general.criteria.SearchCriteria;
import com.example.javaserver.general.model.Message;
import com.example.javaserver.general.service.RequestHandlerService;
import com.example.javaserver.user.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;

@RestController
@RequestMapping("/department")
public class DepartmentController {
    private final RequestHandlerService requestHandlerService;
    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(RequestHandlerService requestHandlerService, DepartmentService departmentService) {
        this.requestHandlerService = requestHandlerService;
        this.departmentService = departmentService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Secured({"ADMIN"})
    public Message create(
            @RequestBody DepartmentIn departmentIn
    ) {
        return departmentService.create(departmentIn);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ADMIN"})
    public Message delete(
            @RequestBody Set<Long> ids
    ) {
        return departmentService.delete(ids);
    }

    @PatchMapping
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
    public Collection<Department> search() {
        return departmentService.getAll();
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
    public Collection<Department> criteriaSearch(
            @RequestBody Set<SearchCriteria> criteria
    ) {
        return departmentService.criteriaSearch(criteria);
    }

    @PostMapping("/search-by-ids")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    public Collection<Department> searchByIds(
            @RequestBody Set<Long> ids
    ) {
        return departmentService.searchByIds(ids);
    }
}
