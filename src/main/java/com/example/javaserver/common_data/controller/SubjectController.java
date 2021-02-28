package com.example.javaserver.common_data.controller;

import com.example.javaserver.common_data.controller.client_model.SubjectIn;
import com.example.javaserver.common_data.model.Subject;
import com.example.javaserver.common_data.service.SubjectService;
import com.example.javaserver.general.service.RequestHandlerService;
import com.example.javaserver.user.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.EnumSet;
import java.util.Set;

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

    @PutMapping
    public ResponseEntity<?> create(
            @RequestHeader("token") String token,
            @RequestBody SubjectIn subjectIn
    ) {
        return requestHandlerService.proceed(
                token,
                (c) -> subjectService.create(subjectIn),
                EnumSet.of(UserRole.ADMIN)
        );
    }

    @GetMapping
    public ResponseEntity<?> searchByUserId(
            @RequestHeader("token") String token,
            @RequestParam("userId") Integer userId
    ) {
        return requestHandlerService.proceed(
                token,
                (c) -> subjectService.searchByUserId(userId),
                EnumSet.allOf(UserRole.class)
        );
    }

    @DeleteMapping
    public ResponseEntity<?> delete(
            @RequestHeader("token") String token,
            @RequestBody Set<Long> ids
    ) {
        return requestHandlerService.proceed(
                token,
                (c) -> subjectService.delete(ids),
                EnumSet.of(UserRole.ADMIN)
        );
    }
}
