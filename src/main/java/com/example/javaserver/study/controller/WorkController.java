package com.example.javaserver.study.controller;

import com.example.javaserver.general.criteria.SearchCriteria;
import com.example.javaserver.general.service.RequestHandlerService;
import com.example.javaserver.study.controller.dto.TaskIn;
import com.example.javaserver.study.controller.dto.WorkIn;
import com.example.javaserver.study.service.WorkService;
import com.example.javaserver.user.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.EnumSet;
import java.util.Set;

@RestController
@RequestMapping("/work")
public class WorkController {
    private final RequestHandlerService requestHandlerService;
    private final WorkService workService;

    @Autowired
    public WorkController(RequestHandlerService requestHandlerService, WorkService workService) {
        this.requestHandlerService = requestHandlerService;
        this.workService = workService;
    }

    @PostMapping
    public ResponseEntity<?> create(
            @RequestHeader("token") String token,
            @RequestBody WorkIn workIn
    ) {
        return requestHandlerService.proceed(
                token,
                (c) -> workService.create(workIn, c),
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
                (c) -> workService.delete(ids),
                EnumSet.of(UserRole.ADMIN, UserRole.TEACHER)
        );
    }

    @PatchMapping
    public ResponseEntity<?> update(
            @RequestHeader("token") String token,
            @RequestBody WorkIn workIn
    ) {
        return requestHandlerService.proceed(
                token,
                (c) -> workService.update(workIn),
                EnumSet.of(UserRole.ADMIN, UserRole.TEACHER)
        );
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll(
            @RequestHeader("token") String token
    ) {
        return requestHandlerService.proceed(
                token,
                (c) -> workService.getAll(),
                EnumSet.allOf(UserRole.class)
        );
    }

    @PostMapping("/criteria-search")
    public ResponseEntity<?> criteriaSearch(
            @RequestHeader("token") String token,
            @RequestBody Set<SearchCriteria> criteria
    ) {
        return requestHandlerService.proceed(
                token,
                (c) -> workService.criteriaSearch(criteria),
                EnumSet.allOf(UserRole.class)
        );
    }

    @PostMapping("/search-by-ids")
    public ResponseEntity<?> searchByIds(
            @RequestHeader("token") String token,
            @RequestBody Set<Long> ids
    ) {
        return requestHandlerService.proceed(
                token,
                (c) -> workService.searchByIds(ids),
                EnumSet.allOf(UserRole.class)
        );
    }
}
