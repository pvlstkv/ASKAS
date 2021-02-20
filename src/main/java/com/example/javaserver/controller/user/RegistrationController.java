package com.example.javaserver.controller.user;

import com.example.javaserver.basemodel.Message;
import com.example.javaserver.config.JwtUtil;
import com.example.javaserver.model.User;
import com.example.javaserver.model.UserRole;
import com.example.javaserver.repo.UserRepo;
import com.example.javaserver.service.user.RequestHandlerService;
import com.example.javaserver.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.EnumSet;

@RestController
public class RegistrationController {

    @Autowired
    private RequestHandlerService requestHandlerService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<?> regUser(@RequestBody User user){
        //return requestHandlerService.proceed(token,() -> userService.regUser(user), EnumSet.of(UserRole.ADMIN));
        return userService.regUser(user);
    }

}
