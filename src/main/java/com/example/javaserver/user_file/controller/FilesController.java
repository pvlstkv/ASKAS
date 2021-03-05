package com.example.javaserver.user_file.controller;

import com.example.javaserver.user.model.UserRole;
import com.example.javaserver.user_file.service.FileService;
import com.example.javaserver.general.service.RequestHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.EnumSet;


@RestController
@RequestMapping("/file")
public class FilesController {
    private final RequestHandlerService requestHandlerService;
    private final FileService fileService;

    @Autowired
    public FilesController(RequestHandlerService requestHandlerService, FileService fileService) {
        this.requestHandlerService = requestHandlerService;
        this.fileService = fileService;
    }

    @PutMapping
    public ResponseEntity<?> uploadFile(
            @RequestHeader("token") String token,
            @RequestParam("fileName") String name,
            @RequestParam("subjectSemesterId") Long subjectSemesterId,
            @RequestParam("file") MultipartFile file
    ) {
        return requestHandlerService.proceed(
                token,
                (c) -> fileService.uploadFile(c, name, subjectSemesterId, file),
                EnumSet.allOf(UserRole.class)
        );
    }

    @GetMapping
    public ResponseEntity<?> uploadFile(
            @RequestHeader("token") String token,
            @RequestParam("fileName") String name
    ) {
        return requestHandlerService.proceed(
                token,
                (c) -> fileService.downloadFile(c, name),
                EnumSet.allOf(UserRole.class)
        );
    }
}
