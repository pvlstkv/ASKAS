package com.example.javaserver.study.controller;

import com.example.javaserver.general.criteria.SearchCriteria;
import com.example.javaserver.general.model.Message;
import com.example.javaserver.general.model.UserDetailsImp;
import com.example.javaserver.study.controller.dto.TaskDto;
import com.example.javaserver.study.controller.mapper.TaskMapper;
import com.example.javaserver.study.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Set;

@RestController
@RequestMapping("/task")
public class TaskController {
    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @Autowired
    public TaskController(TaskService taskService, TaskMapper taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    @ResponseStatus(HttpStatus.OK)
    @Secured({"TEACHER", "ADMIN"})
    @PostMapping
    public TaskDto create(
            @RequestBody @Valid TaskDto taskDto,
            @AuthenticationPrincipal UserDetailsImp userDetails
    ) {
        return taskMapper.toDto(
                taskService.create(
                        taskMapper.toEntity(taskDto),
                        userDetails
                )
        );
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
    @PutMapping
    public TaskDto update(
            @RequestParam("id") Long id,
            @RequestBody @Valid TaskDto taskDto
    ) {
        return taskMapper.toDto(
                taskService.update(
                        id,
                        taskMapper.toEntity(taskDto)
                )
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    @GetMapping("/all")
    public Collection<TaskDto> getAll() {
        return taskMapper.toDto(
                taskService.getAll()
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    @PostMapping("/criteria-search")
    public Collection<TaskDto> criteriaSearch(
            @RequestBody Set<SearchCriteria> criteria
    ) {
        return taskMapper.toDto(
                taskService.criteriaSearch(criteria)
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    @GetMapping("/search-by-ids")
    public Collection<TaskDto> searchByIds(
            @RequestParam("ids") Set<Long> ids
    ) {
        return taskMapper.toDto(
                taskService.getByIds(ids)
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    @GetMapping("/learning")
    public Collection<TaskDto> searchBySubjectAndStudent(
            @RequestParam("subjectId") Long subjectId,
            @RequestParam(value = "studentId", required = false) Integer userId,
            @AuthenticationPrincipal UserDetailsImp userDetails
    ) {
        return taskMapper.toDto(
                taskService.searchBySubjectAndStudent(
                        subjectId,
                        userId,
                        userDetails
                )
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    @GetMapping("/teaching")
    public Collection<TaskDto> searchBySubject(
            @RequestParam("subjectId") Long subjectId
    ) {
        return taskMapper.toDto(
                taskService.searchBySubject(subjectId)
        );
    }
}
