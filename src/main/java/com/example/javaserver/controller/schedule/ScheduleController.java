package com.example.javaserver.controller.schedule;

import com.example.javaserver.service.schedule.ParserService;
import com.example.javaserver.service.schedule.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScheduleController {

    @Autowired
    ParserService parserService;

    @Autowired
    ScheduleService scheduleService;

    @GetMapping("/group")
    public ResponseEntity<?> getScheduleGroup(
            @RequestParam("nameGroup") String nameGroup
            //@RequestHeader("token") String token,
            //@RequestHeader("nameGroup") String nameGroup
            ){
        parserService.parserGroup("https://www.ulstu.ru/schedule/students/part3/63.html");
        return scheduleService.getScheduleGroup(nameGroup);
    }

}
