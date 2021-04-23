package com.example.javaserver.schedule.controller;

import com.example.javaserver.schedule.controller.dto.GroupTeacher;
import com.example.javaserver.schedule.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/teacher")
public class TeacherController {
    private final TeacherService teacherService;

    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }


    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public Collection<String> getListTeachers(
    ){
        return teacherService.getListTeacher();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public GroupTeacher getScheduleTeacher(
            @RequestParam("nameTeacher") String nameTeacher
    ){
        return teacherService.getScheduleTeacher(nameTeacher);
    }
}
