package com.example.javaserver.common_data.controller;

import com.example.javaserver.common_data.model.Subject;
import com.example.javaserver.common_data.service.SubjectService;
import com.example.javaserver.general.service.RequestHandlerService;
import com.example.javaserver.user.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.EnumSet;

@RestController
@RequestMapping("/subject")
public class SubjectController {
    private final RequestHandlerService requestHandlerService;
    private final SubjectService subjectService;

    @Autowired
    public SubjectController(RequestHandlerService requestHandlerService, SubjectService subjectService) {
        this.requestHandlerService = requestHandlerService;
        this.subjectService = subjectService;
    }

    @PostMapping("/creation")
    public ResponseEntity<?> create(
            @RequestParam("token") String token,
            @RequestBody Subject subject
    ) {
        return requestHandlerService.proceed(
                token,
                (c) -> subjectService.create(subject),
                EnumSet.of(UserRole.ADMIN)
        );
    }
}
