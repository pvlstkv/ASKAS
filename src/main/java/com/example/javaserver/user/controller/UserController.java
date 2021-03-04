package com.example.javaserver.user.controller;

import com.example.javaserver.general.service.RequestHandlerService;
import com.example.javaserver.user.model.UserRole;
import com.example.javaserver.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.EnumSet;

@RestController
@RequestMapping("/user")
public class UserController {
    private final RequestHandlerService requestHandlerService;
    private final UserService userService;

    @Autowired
    public UserController(RequestHandlerService requestHandlerService, UserService userService) {
        this.requestHandlerService = requestHandlerService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> getUserInfo(
            @RequestHeader("token") String token,
            @RequestParam(required = false) Integer id
            ){
        return requestHandlerService.proceed(token,(c) -> userService.getUser(c,id), EnumSet.allOf(UserRole.class));
    }

    @GetMapping("/list")
    public ResponseEntity<?> getUserList(
            @RequestHeader("token") String token
    ){
        return requestHandlerService.proceed(token,(c) -> userService.getListUser(), EnumSet.of(UserRole.ADMIN,UserRole.TEACHER));
    }

    @PatchMapping
    public ResponseEntity<?> patchUser(
            @RequestHeader("token") String token,
            @RequestParam Integer id,
            @RequestParam(required = false) String login,
            @RequestParam(required = false) String password,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String patronymic,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String studyGroupName,
            @RequestParam(required = false) String role

    ){
        return requestHandlerService.proceed(token,(c) -> userService.updateUser(
                id,
                login,
                password,
                firstName,
                lastName,
                patronymic,
                phone,
                studyGroupName,
                role
        ),EnumSet.of(UserRole.ADMIN));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(
            @RequestHeader("token") String token,
            @RequestParam Integer id
    ){
        return requestHandlerService.proceed(token,(c) -> userService.deleteUser(id), EnumSet.of(UserRole.ADMIN));
    }



}
