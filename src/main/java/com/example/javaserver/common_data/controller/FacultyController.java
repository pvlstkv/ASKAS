package com.example.javaserver.common_data.controller;

import com.example.javaserver.common_data.controller.client_model.FacultyIn;
import com.example.javaserver.general.specification.SearchCriteria;
import com.example.javaserver.common_data.service.FacultyService;
import com.example.javaserver.general.service.RequestHandlerService;
import com.example.javaserver.user.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;

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

    @PutMapping
    public ResponseEntity<?> create(
            @RequestHeader("token") String token,
            @RequestBody FacultyIn facultyIn
    ) {
        return requestHandlerService.proceed(
                token,
                (c) -> facultyService.create(facultyIn),
                EnumSet.of(UserRole.ADMIN)
        );
    }

    @GetMapping
    public ResponseEntity<?> search(
            @RequestHeader("token") String token,
            @RequestBody Collection<SearchCriteria> criteria
    ) {
        return requestHandlerService.proceed(
                token,
                (c) -> facultyService.search(criteria),
                EnumSet.allOf(UserRole.class)
        );
    }
}
