package com.example.javaserver.controller.user;

import com.example.javaserver.basemodel.Message;
import com.example.javaserver.model.User;
import com.example.javaserver.model.UserRole;
import com.example.javaserver.repo.UserRepo;
import com.example.javaserver.config.JwtUtil;
import com.example.javaserver.service.user.RequestHandlerService;
import com.example.javaserver.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class AuthorizationController {

    @Autowired
    private RequestHandlerService requestHandlerService;

    @Autowired
    private UserService userService;

    @GetMapping("/hello")
    public ResponseEntity<?> hi(@RequestHeader (name="Authorization") String token){
        return requestHandlerService.proceed(token, () -> new ResponseEntity<>(new Message("Привет я работаю"), HttpStatus.OK),EnumSet.allOf(UserRole.class));
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user){
        return userService.logUser(user);
    }



}
