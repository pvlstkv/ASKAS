package com.example.javaserver.academic_performance.controller;

import com.example.javaserver.academic_performance.model.TaskPerformancePerUser;
import com.example.javaserver.academic_performance.model.main_page.Performance;
import com.example.javaserver.academic_performance.model.TaskPerformance;
import com.example.javaserver.academic_performance.service.PerformanceService;
import com.example.javaserver.general.model.UserDetailsImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.builders.ResponseBuilder;

import java.util.List;

@RestController
@RequestMapping(value = "/api/performance/")
public class PerformanceController {
    private final PerformanceService performanceService;

    @Autowired
    public PerformanceController(PerformanceService performanceService) {
        this.performanceService = performanceService;
    }


    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    @GetMapping
    public Performance formPerformance(
            @AuthenticationPrincipal UserDetailsImp userDetails
    ) {
        return performanceService.formUserPerformance(userDetails.getId(), userDetails);
    }

    @ResponseStatus(HttpStatus.OK)
    @Secured({"TEACHER", "ADMIN"})
    @GetMapping("/by-group-and-task")
    public TaskPerformance formTaskPerformancePerGroup(
            @RequestParam(value = "group_id") Long groupId,
            @RequestParam(value = "task_id") Long taskId
    ) {
        return performanceService.formTaskPerformanceByGroup(groupId, taskId);
    }

    @ResponseStatus(HttpStatus.OK)
    @Secured({"TEACHER", "ADMIN"})
    @GetMapping("/by-group")
    public List<TaskPerformancePerUser> formStudyLogPerGroup(
            @RequestParam(value = "group_id") Long groupId
    ) {
        return performanceService.formStudyLogPerGroup(groupId);
    }

}
