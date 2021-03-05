package com.example.javaserver.user.controller;

import com.example.javaserver.general.config.JwtUtil;
import com.example.javaserver.user.client_model.UserI;
import com.example.javaserver.user.model.UserRole;
import com.example.javaserver.general.service.RequestHandlerService;
import com.example.javaserver.user.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/registration")
    public ResponseEntity<?> regUser(
            @RequestHeader("token") String token,
            @RequestBody UserI userI
    ){
        return requestHandlerService.proceed(token,(c) -> authService.regUser(userI), EnumSet.of(UserRole.ADMIN));
    }

}
