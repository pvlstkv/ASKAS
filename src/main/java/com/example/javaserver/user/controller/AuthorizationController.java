package com.example.javaserver.user.controller;

import com.example.javaserver.general.model.Message;
import com.example.javaserver.user.controller.dto.TokenIO;
import com.example.javaserver.user.controller.dto.UserDto;
import com.example.javaserver.user.controller.mapper.UserMapper;
import com.example.javaserver.user.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthorizationController {
    private final UserMapper userMapper;
    private final AuthService authService;

    @Autowired
    public AuthorizationController(UserMapper user, AuthService authService) {
        this.userMapper = user;
        this.authService = authService;
    }


    @GetMapping("/hello")
    @ResponseStatus(HttpStatus.OK)
    public Message hi(){
        return new Message("Привет я работаю");
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public TokenIO loginUser(@RequestBody UserDto userDto){
        return authService.logUser(
                userMapper.toEntity(userDto)
        );
    }



}
