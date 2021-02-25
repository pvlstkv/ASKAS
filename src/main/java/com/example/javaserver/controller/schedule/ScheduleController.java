package com.example.javaserver.controller.schedule;

import com.example.javaserver.model.UserRole;
import com.example.javaserver.service.schedule.ParserService;
import com.example.javaserver.service.schedule.ScheduleService;
import com.example.javaserver.service.user.RequestHandlerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.EnumSet;

@RestController
public class ScheduleController {
    private final RequestHandlerService requestHandlerService;
    private final ParserService parserService;
    private final ScheduleService scheduleService;

    public ScheduleController(RequestHandlerService requestHandlerService, ParserService parserService, ScheduleService scheduleService) {
        this.requestHandlerService = requestHandlerService;
        this.parserService = parserService;
        this.scheduleService = scheduleService;
    }

    @GetMapping("/group")
    public ResponseEntity<?> getScheduleGroup(
            @RequestParam("nameGroup") String nameGroup
            ){
        return scheduleService.getScheduleGroup(nameGroup);
    }

    @GetMapping("/group-list")
    public ResponseEntity<?> getListScheduleGroups(){
        return scheduleService.getListScheduleGroups();
    }

    @PostMapping("/group-update")
    public ResponseEntity<?> updateAllGroup(@RequestHeader("token") String token){
        return requestHandlerService.proceed(token,(с) -> scheduleService.iteratingThroughGroups(), EnumSet.of(UserRole.ADMIN));
    }

}
