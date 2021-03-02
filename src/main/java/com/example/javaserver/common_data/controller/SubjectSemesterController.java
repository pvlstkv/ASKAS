package com.example.javaserver.common_data.controller;

import com.example.javaserver.common_data.controller.client_model.SubjectSemesterIn;
import com.example.javaserver.common_data.service.SubjectSemesterService;
import com.example.javaserver.general.criteria.SearchCriteria;
import com.example.javaserver.general.service.RequestHandlerService;
import com.example.javaserver.user.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.EnumSet;
import java.util.Set;

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

    @PutMapping
    public ResponseEntity<?> create(
            @RequestHeader("token") String token,
            @RequestBody SubjectSemesterIn subjectSemesterIn
    ) {
        return requestHandlerService.proceed(
                token,
                (c) -> subjectSemesterService.create(subjectSemesterIn),
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
                (c) -> subjectSemesterService.delete(ids),
                EnumSet.of(UserRole.ADMIN)
        );
    }

    @PatchMapping("/assign")
    public ResponseEntity<?> setSubject(
            @RequestHeader("token") String token,
            @RequestParam("subjectSemesterId") Long subjectSemesterId,
            @RequestParam("subjectId") Long subjectId
    ) {
        return requestHandlerService.proceed(
                token,
                (c) -> subjectSemesterService.setSubject(subjectSemesterId, subjectId),
                EnumSet.of(UserRole.ADMIN)
        );
    }

    @GetMapping("/all")
    public ResponseEntity<?> search(
            @RequestHeader("token") String token
    ) {
        return requestHandlerService.proceed(
                token,
                (c) -> subjectSemesterService.getAll(),
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
                (c) -> subjectSemesterService.search(criteria),
                EnumSet.allOf(UserRole.class)
        );
    }
}
