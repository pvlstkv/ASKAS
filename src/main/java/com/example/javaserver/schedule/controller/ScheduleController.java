package com.example.javaserver.schedule.controller;

import com.example.javaserver.general.service.RequestHandlerService;
import com.example.javaserver.schedule.service.ScheduleService;
import com.example.javaserver.user.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.EnumSet;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    private final RequestHandlerService requestHandlerService;
    //private final ParserService parserService;
    private final ScheduleService scheduleService;

    @Autowired
    public ScheduleController(RequestHandlerService requestHandlerService,/* ParserService parserService,*/ ScheduleService scheduleService) {
        this.requestHandlerService = requestHandlerService;
        /*this.parserService = parserService;*/
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
    public ResponseEntity<?> updateAllGroup(@RequestHeader("token") String token){
        return requestHandlerService.proceed(token,(с) -> scheduleService.iteratingThroughGroups(), EnumSet.of(UserRole.ADMIN));
    }

}
