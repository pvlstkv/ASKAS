package com.example.javaserver.study.controller;

import com.example.javaserver.general.service.RequestHandlerService;
import com.example.javaserver.study.controller.dto.TaskIn;
import com.example.javaserver.study.service.TaskService;
import com.example.javaserver.user.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.EnumSet;

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
}
