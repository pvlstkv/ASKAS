package com.example.javaserver.study.controller;

import com.example.javaserver.general.service.RequestHandlerService;
import com.example.javaserver.study.service.UserFileService;
import com.example.javaserver.user.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.EnumSet;

@RestController
@RequestMapping("/file")
public class UserFileController {
    private final RequestHandlerService requestHandlerService;
    private final UserFileService userFileService;

    @Autowired
    public UserFileController(RequestHandlerService requestHandlerService, UserFileService userFileService) {
        this.requestHandlerService = requestHandlerService;
        this.userFileService = userFileService;
    }

    @PostMapping
    public ResponseEntity<?> create(
            @RequestHeader("token") String token,
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "accessLevel", required = false) UserRole accessLevel
    ) {
        return requestHandlerService.proceed(
                token,
                (c) -> userFileService.create(file, accessLevel, c),
                EnumSet.allOf(UserRole.class)
        );
    }

    @GetMapping
    public ResponseEntity<?> getById(
            @RequestHeader("token") String token,
            @RequestParam("fileId") Long id
    ) {
        return requestHandlerService.proceed(
                token,
                (c) -> userFileService.getById(id, c),
                EnumSet.allOf(UserRole.class)
        );
    }
}
