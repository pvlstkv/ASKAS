package com.example.javaserver.common_data.controller;

import com.example.javaserver.common_data.controller.client_model.SubjectIn;
import com.example.javaserver.common_data.service.SubjectService;
import com.example.javaserver.general.criteria.SearchCriteria;
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

    @PatchMapping
    public ResponseEntity<?> update(
            @RequestHeader("token") String token,
            @RequestParam("id") Long id,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "decryption", required = false) String decryption,
            @RequestParam(value = "departmentId", required = false) String departmentId
    ) {
        return requestHandlerService.proceed(
                token,
                (c) -> subjectService.update(id, name, decryption, departmentId),
                EnumSet.of(UserRole.ADMIN)
        );
    }

    @GetMapping("/all")
    public ResponseEntity<?> search(
            @RequestHeader("token") String token
    ) {
        return requestHandlerService.proceed(
                token,
                (c) -> subjectService.getAll(),
                EnumSet.allOf(UserRole.class)
        );
    }

    @GetMapping("/criteria-search")
    public ResponseEntity<?> criteriaSearch(
            @RequestHeader("token") String token,
            @RequestBody Set<SearchCriteria> criteria
    ) {
        return requestHandlerService.proceed(
                token,
                (c) -> subjectService.criteriaSearch(criteria),
                EnumSet.allOf(UserRole.class)
        );
    }

    @GetMapping("/search-by-ids")
    public ResponseEntity<?> searchByIds(
            @RequestHeader("token") String token,
            @RequestBody Set<Long> ids
    ) {
        return requestHandlerService.proceed(
                token,
                (c) -> subjectService.searchByIds(ids),
                EnumSet.allOf(UserRole.class)
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

    @PostMapping
    public ResponseEntity<?> addTeachers(
            @RequestHeader("token") String token,
            @RequestParam("subjectId") Long subjectId,
            @RequestBody Set<Integer> userIds
    ){
        return requestHandlerService.proceed(
                token,
                (c) -> subjectService.addTeachers(subjectId,userIds),
                EnumSet.of(UserRole.ADMIN)
        );
    }

}
