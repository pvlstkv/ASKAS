package com.example.javaserver.user.controller;

import com.example.javaserver.user.controller.dto.UserContactDto;
import com.example.javaserver.user.controller.mapper.UserContactMapper;
import com.example.javaserver.user.service.UserContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Set;

@RestController
@RequestMapping("/user-contact")
@ResponseStatus(HttpStatus.OK)
public class UserContactController {
    private final UserContactService userContactService;
    private final UserContactMapper userContactMapper;

    @Autowired
    public UserContactController(UserContactService userContactService, UserContactMapper userContactMapper) {
        this.userContactService = userContactService;
        this.userContactMapper = userContactMapper;
    }

    @PostMapping
    public UserContactDto create(
            @RequestBody @Valid UserContactDto userContactDto
    ) {
        return userContactMapper.toDto(
                userContactService.create(
                        userContactMapper.toEntity(userContactDto)
                )
        );
    }

    @DeleteMapping
    public Collection<UserContactDto> delete(
            @RequestParam("ids") Set<Long> ids
    ) {
        return userContactMapper.toDto(
                userContactService.delete(ids)
        );
    }

    @GetMapping("/search-by-ids")
    public Collection<UserContactDto> getByIds(
            @RequestParam("ids") Set<Long> ids
    ) {
        return userContactMapper.toDto(
                userContactService.getByIds(ids)
        );
    }

    @GetMapping("/search-by-user")
    public Collection<UserContactDto> getByUserId(
            @RequestParam("userId") Integer userId
    ) {
        return userContactMapper.toDto(
                userContactService.getByUserId(userId)
        );
    }
}
