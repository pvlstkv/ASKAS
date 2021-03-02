package com.example.javaserver.common_data.controller;

import com.example.javaserver.common_data.controller.client_model.DepartmentIn;
import com.example.javaserver.common_data.model.Department;
import com.example.javaserver.common_data.service.DepartmentService;
import com.example.javaserver.general.criteria.SearchCriteria;
import com.example.javaserver.general.service.RequestHandlerService;
import com.example.javaserver.user.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping
    public ResponseEntity<?> create(
            @RequestHeader("token") String token,
            @RequestBody DepartmentIn departmentIn
    ) {
        return requestHandlerService.proceed(
                token,
                (c) -> departmentService.create(departmentIn),
                EnumSet.of(UserRole.ADMIN)
        );
    }

    @DeleteMapping
    public ResponseEntity<?> delete(
            @RequestHeader("token") String token,
            @RequestBody Set<Long> ids
    ) {
        return requestHandlerService.proceed(
                token,
                (c) -> departmentService.delete(ids),
                EnumSet.of(UserRole.ADMIN)
        );
    }

    @GetMapping("/all")
    public ResponseEntity<?> search(
            @RequestHeader("token") String token
    ) {
        return requestHandlerService.proceed(
                token,
                (c) -> departmentService.getAll(),
                EnumSet.allOf(UserRole.class)
        );
    }

    @GetMapping("/all/short-names")
    public ResponseEntity<?> searchAllShortNames(
            @RequestHeader("token") String token
    ) {
        return requestHandlerService.proceed(
                token,
                (c) -> departmentService.getAllShortNames(),
                EnumSet.allOf(UserRole.class)
        );
    }

    @GetMapping("/criteria-search")
    public ResponseEntity<?> search(
            @RequestHeader("token") String token,
            @RequestBody Set<SearchCriteria> criteria
    ) {
        return requestHandlerService.proceed(
                token,
                (c) -> departmentService.search(criteria),
                EnumSet.allOf(UserRole.class)
        );
    }
}
