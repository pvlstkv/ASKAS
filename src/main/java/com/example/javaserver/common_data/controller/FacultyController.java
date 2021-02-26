package com.example.javaserver.common_data.controller;

import com.example.javaserver.user.model.UserRole;
import com.example.javaserver.common_data.model.Faculty;
import com.example.javaserver.common_data.service.FacultyService;
import com.example.javaserver.general.service.RequestHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.EnumSet;

@RestController
public class FacultyController {
    private final RequestHandlerService requestHandlerService;
    private final FacultyService facultyService;

    @Autowired
    public FacultyController(RequestHandlerService requestHandlerService, FacultyService facultyService) {
        this.requestHandlerService = requestHandlerService;
        this.facultyService = facultyService;
    }

    @PostMapping("/faculty/new")
    public ResponseEntity<?> createFaculty(
            @RequestHeader("token") String token,
            @RequestBody Faculty faculty
    ) {
        return requestHandlerService.proceed(
                token,
                (c) -> facultyService.createFaculty(faculty),
                EnumSet.of(UserRole.ADMIN)
        );
    }
}
