package com.example.javaserver.study.controller;

import com.example.javaserver.general.criteria.SearchCriteria;
import com.example.javaserver.general.model.Message;
import com.example.javaserver.general.model.UserDetailsImp;
import com.example.javaserver.study.controller.dto.TaskIn;
import com.example.javaserver.study.model.Task;
import com.example.javaserver.study.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Set;

@RestController
@RequestMapping("/task")
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
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
    public Message update(@RequestBody TaskIn taskIn) {
        return taskService.update(taskIn);
    }

    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    @GetMapping("/all")
    public Collection<Task> getAll() {
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
            @RequestBody Set<Long> ids
    ) {
        return taskService.searchByIds(ids);
    }

    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    @GetMapping("/learning")
    public Collection<Task> searchBySubjectAndStudent(
            @RequestParam("subjectId") Long subjectId,
            @RequestParam(value = "userId", required = false) Integer userId,
            @AuthenticationPrincipal UserDetailsImp userDetails
    ) {
        return taskService.searchBySubjectAndStudent(subjectId, userId, userDetails);
    }

    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    @GetMapping
    public Collection<Task> searchBySubjectAndTeacher(
            @RequestParam("subjectId") Long subjectId
    ) {
        return taskService.searchBySubjectAndTeacher(subjectId);
    }
}
