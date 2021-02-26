package com.example.javaserver.common_data.controller;

import com.example.javaserver.common_data.model.SubjectSemester;
import com.example.javaserver.common_data.service.SubjectSemesterService;
import com.example.javaserver.general.service.RequestHandlerService;
import com.example.javaserver.user.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.EnumSet;

@RestController
@RequestMapping("/subject-semester")
public class SubjectSemesterController {
    private final RequestHandlerService requestHandlerService;
    private final SubjectSemesterService subjectSemesterService;

    @Autowired
    public SubjectSemesterController(RequestHandlerService requestHandlerService, SubjectSemesterService subjectSemesterService) {
        this.requestHandlerService = requestHandlerService;
        this.subjectSemesterService = subjectSemesterService;
    }

    @PostMapping("/creation")
    public ResponseEntity<?> create(
            @RequestHeader("token") String token,
            @RequestBody SubjectSemester subjectSemester
    ) {
        return requestHandlerService.proceed(
                token,
                (c) -> subjectSemesterService.create(subjectSemester),
                EnumSet.of(UserRole.ADMIN)
        );
    }

    @PostMapping("/subject")
    public ResponseEntity<?> setSubject(
            @RequestHeader("token") String token,
            @RequestParam("subject_semester_id") Long subjectSemesterId,
            @RequestParam("subject_id") Long subjectId
    ) {
        return requestHandlerService.proceed(
                token,
                (c) -> subjectSemesterService.setSubject(subjectSemesterId, subjectId),
                EnumSet.of(UserRole.ADMIN)
        );
    }
}
