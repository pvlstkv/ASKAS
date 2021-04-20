package com.example.javaserver.study.controller;

import com.example.javaserver.general.model.UserDetailsImp;
import com.example.javaserver.study.controller.dto.UserFileDto;
import com.example.javaserver.study.controller.mapper.UserFileMapper;
import com.example.javaserver.study.model.UserFile;
import com.example.javaserver.study.service.UserFileService;
import com.example.javaserver.user.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("/files")
public class UserFileController {
    private final UserFileService userFileService;
    private final UserFileMapper userFileMapper;

    @Autowired
    public UserFileController(UserFileService userFileService, UserFileMapper userFileMapper) {
        this.userFileService = userFileService;
        this.userFileMapper = userFileMapper;
    }

    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    @PostMapping
    public UserFileDto upload(
            @AuthenticationPrincipal UserDetailsImp userDetails,
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "accessLevel", required = false) UserRole accessLevel
    ) {
        UserFile userFile = userFileService.upload(file, accessLevel, userDetails);
        return userFileMapper.toDto(userFile);
    }

    //@ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    @GetMapping
    public ResponseEntity<ByteArrayResource> download(
            @RequestParam("id") Long id,
            @AuthenticationPrincipal UserDetailsImp userDetails
    ) {
        return userFileService.download(id, userDetails);
    }

    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    @GetMapping("/search-by")
    public Set<UserFile> getByIds(
            @RequestParam("ids") Set<Long> ids
    ) {
        return userFileService.getBy(ids);
    }

    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    @PostMapping("/dummy")
    public UserFileDto getByIds(
            @RequestBody @Valid UserFileDto userFileDto
    ) {
        UserFile userFile = userFileMapper.toEntity(userFileDto);
        //UserRole role = userFile.getUser().getRole();
        return userFileMapper.toDto(userFile);
    }
}
