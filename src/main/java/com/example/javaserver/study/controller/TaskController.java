package com.example.javaserver.study.controller;

import com.example.javaserver.general.criteria.SearchCriteria;
import com.example.javaserver.general.model.Message;
import com.example.javaserver.general.model.UserDetailsImp;
import com.example.javaserver.general.service.RequestHandlerService;
import com.example.javaserver.study.controller.dto.TaskIn;
import com.example.javaserver.study.model.Task;
import com.example.javaserver.study.service.TaskService;
import com.example.javaserver.user.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
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

    @ResponseStatus(HttpStatus.OK)
    @Secured({"TEACHER", "ADMIN"})
    @PostMapping
    public Message create(
            @AuthenticationPrincipal UserDetailsImp userDetails,
            @RequestBody TaskIn taskIn
    ) {
        return taskService.create(taskIn, userDetails);
    }

    @ResponseStatus(HttpStatus.OK)
    @Secured({"TEACHER", "ADMIN"})
    @DeleteMapping
    public Message delete(
            @RequestBody Set<Long> ids
    ) {
        return taskService.delete(ids);
    }

    @ResponseStatus(HttpStatus.OK)
    @Secured({"TEACHER", "ADMIN"})
    @PatchMapping
    public Message update(
            @RequestHeader("token") String token,
            @RequestBody TaskIn taskIn
    ) {
        return taskService.update(taskIn);
    }

    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    @GetMapping("/all")
    public Collection<Task> getAll(
            @RequestHeader("token") String token
    ) {
        return taskService.getAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    @PostMapping("/criteria-search")
    public Collection<Task> criteriaSearch(
            @RequestBody Set<SearchCriteria> criteria
    ) {
        return taskService.criteriaSearch(criteria);
    }

    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    @PostMapping("/search-by-ids")
    public Collection<Task> searchByIds(
            @RequestHeader("token") String token,
            @RequestBody Set<Long> ids
    ) {
        return taskService.searchByIds(ids);
    }

    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    @GetMapping
    public Collection<Task> searchBySubjectAndUser(
            @RequestParam("subjectId") Long subjectId,
            @RequestParam(value = "userId", required = false) Integer userId,
            @AuthenticationPrincipal UserDetailsImp userDetails
    ) {
        return taskService.searchBySubjectAndUser(subjectId, userId, userDetails);
    }
}
