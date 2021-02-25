package com.example.javaserver.controller.common_data;

import com.example.javaserver.model.UserRole;
import com.example.javaserver.model.common_data.Faculty;
import com.example.javaserver.service.common_data.FacultyService;
import com.example.javaserver.service.user.RequestHandlerService;
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
