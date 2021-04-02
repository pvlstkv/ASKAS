package com.example.javaserver.study.controller;

import com.example.javaserver.general.model.UserDetailsImp;
import com.example.javaserver.general.service.RequestHandlerService;
import com.example.javaserver.study.controller.dto.UserFileOut;
import com.example.javaserver.study.model.UserFile;
import com.example.javaserver.study.service.UserFileService;
import com.example.javaserver.user.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.EnumSet;
import java.util.Set;

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

    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    @PostMapping
    public UserFileOut create(
            @AuthenticationPrincipal UserDetailsImp userDetails,
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "accessLevel", required = false) UserRole accessLevel
    ) {
        return userFileService.create(file, accessLevel, userDetails);
    }

    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    @GetMapping
    public byte[] download(
            @AuthenticationPrincipal UserDetailsImp userDetails,
            @RequestParam("id") Long id
    ) {
        return userFileService.download(id, userDetails);
    }

    @GetMapping("/search-by")
    public Set<UserFile> getById(
            @RequestParam("id") Long[] ids
    ) {
        return userFileService.getBy(ids);
    }
}
