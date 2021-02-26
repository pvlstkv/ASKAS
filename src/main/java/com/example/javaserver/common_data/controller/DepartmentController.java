package com.example.javaserver.common_data.controller;

import com.example.javaserver.common_data.model.Department;
import com.example.javaserver.common_data.service.DepartmentService;
import com.example.javaserver.general.service.RequestHandlerService;
import com.example.javaserver.user.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.EnumSet;

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

    @PostMapping("/creation")
    public ResponseEntity<?> create(
            @RequestHeader("token") String token,
            @RequestBody Department department
    ) {
        return requestHandlerService.proceed(
                token,
                (c) -> departmentService.create(department),
                EnumSet.of(UserRole.ADMIN)
        );
    }
}
