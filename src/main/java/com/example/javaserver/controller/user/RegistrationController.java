package com.example.javaserver.controller.user;

import com.example.javaserver.config.JwtUtil;
import com.example.javaserver.model.User;
import com.example.javaserver.model.UserRole;
import com.example.javaserver.service.user.RequestHandlerService;
import com.example.javaserver.service.user.UserService;
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
        return requestHandlerService.proceed(token,(с) -> userService.regUser(user), EnumSet.of(UserRole.ADMIN));
    }

}
