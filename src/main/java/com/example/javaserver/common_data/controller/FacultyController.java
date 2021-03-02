package com.example.javaserver.common_data.controller;

import com.example.javaserver.common_data.controller.client_model.FacultyIn;
import com.example.javaserver.general.criteria.SearchCriteria;
import com.example.javaserver.common_data.service.FacultyService;
import com.example.javaserver.general.service.RequestHandlerService;
import com.example.javaserver.user.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping
    public ResponseEntity<?> delete(
            @RequestHeader("token") String token,
            @RequestBody Set<Long> ids
    ) {
        return requestHandlerService.proceed(
                token,
                (c) -> facultyService.delete(ids),
                EnumSet.of(UserRole.ADMIN)
        );
    }

    @PatchMapping
    public ResponseEntity<?> update(
            @RequestHeader("token") String token,
            @RequestParam("id") Long id
    ) {
        return requestHandlerService.proceed(
                token,
                (c) -> facultyService.update(),
                EnumSet.of(UserRole.ADMIN)
        );
    }

    @GetMapping("/all")
    public ResponseEntity<?> searchByCriteria(
            @RequestHeader("token") String token
    ) {
        return requestHandlerService.proceed(
                token,
                (c) -> facultyService.getAll(),
                EnumSet.allOf(UserRole.class)
        );
    }

    @GetMapping
    public ResponseEntity<?> searchByExamples(
            @RequestHeader("token") String token,
            @RequestBody Set<FacultyIn> examples
    ) {
        return requestHandlerService.proceed(
                token,
                (c) -> facultyService.searchByExamples(examples),
                EnumSet.allOf(UserRole.class)
        );
    }

    @GetMapping("/criteria-search")
    public ResponseEntity<?> searchByCriteria(
            @RequestHeader("token") String token,
            @RequestBody Set<SearchCriteria> criteria
    ) {
        return requestHandlerService.proceed(
                token,
                (c) -> facultyService.searchByCriteria(criteria),
                EnumSet.allOf(UserRole.class)
        );
    }
}
