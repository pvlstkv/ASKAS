package com.example.javaserver.user.controller;

import com.example.javaserver.general.config.JwtUtil;
import com.example.javaserver.user.model.User;
import com.example.javaserver.user.model.UserRole;
import com.example.javaserver.general.service.RequestHandlerService;
import com.example.javaserver.user.service.UserService;
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
    private final UserService userService;

    @Autowired
    public RegistrationController(RequestHandlerService requestHandlerService, JwtUtil jwtUtil, UserService userService) {
        this.requestHandlerService = requestHandlerService;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @PostMapping("/registration")
    public ResponseEntity<?> regUser(
            @RequestHeader("token") String token,
            @RequestBody User user
    ){
        return requestHandlerService.proceed(token,(c) -> userService.regUser(user), EnumSet.of(UserRole.ADMIN));
    }

}
