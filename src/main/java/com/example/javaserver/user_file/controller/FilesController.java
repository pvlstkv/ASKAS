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
public class FilesController {
    private final RequestHandlerService requestHandlerService;
    private final FileService fileService;

    @Autowired
    public FilesController(RequestHandlerService requestHandlerService, FileService fileService) {
        this.requestHandlerService = requestHandlerService;
        this.fileService = fileService;
    }

    @PostMapping("/file/uploading")
    public ResponseEntity<?> uploadFile(
            @RequestHeader("token") String token,
            @RequestParam("fileName") String name,
            @RequestParam("subjectName") String subjectName,
            @RequestParam("file") MultipartFile file
    ) {
        return requestHandlerService.proceed(
                token,
                (c) -> fileService.uploadFile(c, name, subjectName, file),
                EnumSet.allOf(UserRole.class)
        );
    }

    @GetMapping("/file")
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
