package com.example.javaserver.user.controller;

import com.example.javaserver.general.model.Message;
import com.example.javaserver.user.model.User;
import com.example.javaserver.general.service.RequestHandlerService;
import com.example.javaserver.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthorizationController {
    private final RequestHandlerService requestHandlerService;
    private final UserService userService;

    @Autowired
    public AuthorizationController(RequestHandlerService requestHandlerService, UserService userService) {
        this.requestHandlerService = requestHandlerService;
        this.userService = userService;
    }

    @GetMapping("/hello")
    public ResponseEntity<?> hi(){
        return new ResponseEntity<>(new Message("Привет я работаю"),HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user){
        return userService.logUser(user);
    }



}
