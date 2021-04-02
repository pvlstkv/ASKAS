package com.example.javaserver.user.controller;

import com.example.javaserver.general.config.JwtUtil;
import com.example.javaserver.general.model.Message;
import com.example.javaserver.user.client_model.UserI;
import com.example.javaserver.user.model.UserRole;
import com.example.javaserver.general.service.RequestHandlerService;
import com.example.javaserver.user.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.EnumSet;

@RestController
public class RegistrationController {
    private final RequestHandlerService requestHandlerService;
    private final JwtUtil jwtUtil;
    private final AuthService authService;

    @Autowired
    public RegistrationController(RequestHandlerService requestHandlerService, JwtUtil jwtUtil, AuthService authService) {
        this.requestHandlerService = requestHandlerService;
        this.jwtUtil = jwtUtil;
        this.authService = authService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @Secured({"ADMIN"})
    @PostMapping("/registration")
    public Message regUser(
            @RequestHeader("token") String token,
            @RequestBody UserI userI
    ){
        return authService.regUser(userI);
    }

}
