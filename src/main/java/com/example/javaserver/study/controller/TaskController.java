package com.example.javaserver.study.controller;

import com.example.javaserver.general.criteria.SearchCriteria;
import com.example.javaserver.general.service.RequestHandlerService;
import com.example.javaserver.study.controller.dto.TaskIn;
import com.example.javaserver.study.service.TaskService;
import com.example.javaserver.user.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.EnumSet;
import java.util.Set;

@RestController
@RequestMapping("/task")
public class TaskController {
    private final RequestHandlerService requestHandlerService;
    private final TaskService taskService;

    @Autowired
    public TaskController(RequestHandlerService requestHandlerService, TaskService taskService) {
        this.requestHandlerService = requestHandlerService;
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<?> create(
            @RequestHeader("token") String token,
            @RequestBody TaskIn taskIn
    ) {
        return requestHandlerService.proceed(
                token,
                (c) -> taskService.create(taskIn, c),
                EnumSet.of(UserRole.ADMIN, UserRole.TEACHER)
        );
    }

    @DeleteMapping
    public ResponseEntity<?> delete(
            @RequestHeader("token") String token,
            @RequestBody Set<Long> ids
    ) {
        return requestHandlerService.proceed(
                token,
                (c) -> taskService.delete(ids),
                EnumSet.of(UserRole.ADMIN, UserRole.TEACHER)
        );
    }

    @PatchMapping
    public ResponseEntity<?> update(
            @RequestHeader("token") String token,
            @RequestBody TaskIn taskIn
    ) {
        return requestHandlerService.proceed(
                token,
                (c) -> taskService.update(taskIn),
                EnumSet.of(UserRole.ADMIN)
        );
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll(
            @RequestHeader("token") String token
    ) {
        return requestHandlerService.proceed(
                token,
                (c) -> taskService.getAll(),
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
                (c) -> taskService.criteriaSearch(criteria),
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
                (c) -> taskService.searchByIds(ids),
                EnumSet.allOf(UserRole.class)
        );
    }
}
