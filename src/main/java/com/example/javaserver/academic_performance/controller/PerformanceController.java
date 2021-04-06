package com.example.javaserver.academic_performance.controller;

import com.example.javaserver.academic_performance.model.Performance;
import com.example.javaserver.academic_performance.service.PerformanceService;
import com.example.javaserver.general.model.UserDetailsImp;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/performance/")
public class PerformanceController {
    private final PerformanceService performanceService;

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

}
