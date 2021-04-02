package com.example.javaserver.schedule.controller;

import com.example.javaserver.general.model.Message;
import com.example.javaserver.general.service.RequestHandlerService;
import com.example.javaserver.schedule.service.ScheduleService;
import com.example.javaserver.user.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.EnumSet;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping
    public ResponseEntity<?> getScheduleGroup(
            @RequestParam("nameGroup") String nameGroup
            ){
        return scheduleService.getScheduleGroup(nameGroup);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getListScheduleGroups(){
        return scheduleService.getListScheduleGroups();
    }

    @PostMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ADMIN"})
    public Message updateAllGroup(){
        return scheduleService.iteratingThroughGroups();
    }

}
