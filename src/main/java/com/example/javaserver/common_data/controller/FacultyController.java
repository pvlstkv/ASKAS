package com.example.javaserver.common_data.controller;

import com.example.javaserver.user.model.UserRole;
import com.example.javaserver.common_data.model.Faculty;
import com.example.javaserver.common_data.service.FacultyService;
import com.example.javaserver.general.service.RequestHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.EnumSet;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final RequestHandlerService requestHandlerService;
    private final FacultyService facultyService;

    @Autowired
    public FacultyController(RequestHandlerService requestHandlerService, FacultyService facultyService) {
        this.requestHandlerService = requestHandlerService;
        this.facultyService = facultyService;
    }

    @PostMapping("/creation")
    public ResponseEntity<?> create(
            @RequestHeader("token") String token,
            @RequestBody Faculty faculty
    ) {
        return requestHandlerService.proceed(
                token,
                (c) -> facultyService.create(faculty),
                EnumSet.of(UserRole.ADMIN)
        );
    }
}
