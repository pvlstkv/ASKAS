package com.example.javaserver.study.controller;

import com.example.javaserver.general.model.UserDetailsImp;
import com.example.javaserver.study.model.UserFile;
import com.example.javaserver.study.service.UserFileService;
import com.example.javaserver.user.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@RestController
@RequestMapping("/files")
public class UserFileController {
    private final UserFileService userFileService;

    @Autowired
    public UserFileController(UserFileService userFileService) {
        this.userFileService = userFileService;
    }

    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    @PostMapping
    public UserFile upload(
            @AuthenticationPrincipal UserDetailsImp userDetails,
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "accessLevel", required = false) UserRole accessLevel
    ) {
        return userFileService.upload(file, accessLevel, userDetails);
    }

    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    @GetMapping
    public Resource download(
            @RequestParam("id") Long id,
            @AuthenticationPrincipal UserDetailsImp userDetails
    ) {
        return userFileService.download(id, userDetails);
    }

    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    @GetMapping("/search-by")
    public Set<UserFile> getById(
            @RequestParam("id") Set<Long> ids
    ) {
        return userFileService.getBy(ids);
    }
}