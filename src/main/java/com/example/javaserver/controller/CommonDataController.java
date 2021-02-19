package com.example.javaserver.controller;

import com.example.javaserver.configuration.Role;
import com.example.javaserver.model.Consumer;
import com.example.javaserver.model.commonData.Subject;
import com.example.javaserver.service.commonData.ConsumerService;
import com.example.javaserver.service.commonData.SemesterService;
import com.example.javaserver.service.commonData.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommonDataController {

    @Autowired
    private ConsumerService consumerService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private SemesterService semesterService;

    @GetMapping("/get/user")
    public ResponseEntity<?> getUser() {

        return new ResponseEntity<>(new Consumer(1, "user", "qwe@qwe.com", "88005553535",
                "password", "Ivan", "Ivanoov", "Patronymic", "avatarUrl",
                Role.Admin, 3, null, null, null), HttpStatus.OK);
    }

    @PostMapping("/add/user")
    public ResponseEntity<?> addUser(@RequestBody Consumer consumer) {
        consumerService.addConsumer(consumer);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/add/subject")
    public ResponseEntity<?> addSubject(@RequestBody Subject subject) {
        subjectService.addSubject(subject);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/add/semester")
    public ResponseEntity<?> startStudying(@RequestParam(value = "id") Long id) {
        if (semesterService.startStudying(id))
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
