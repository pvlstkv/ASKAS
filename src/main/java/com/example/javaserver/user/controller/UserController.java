package com.example.javaserver.user.controller;

import com.example.javaserver.general.service.RequestHandlerService;
import com.example.javaserver.user.client_model.UserI;
import com.example.javaserver.user.dto.UpdateUser;
import com.example.javaserver.user.model.User;
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

    @PutMapping
    public ResponseEntity<?> putUser(
            @RequestHeader("token") String token,
            @RequestBody UpdateUser updateUser
    ){
        return requestHandlerService.proceed(token,userContext -> userService.putUser(userContext,updateUser),EnumSet.allOf(UserRole.class));
    }


    @PatchMapping
    public ResponseEntity<?> patchUser(
            @RequestHeader("token") String token,
            @RequestBody UserI userI


            ){
        return requestHandlerService.proceed(token,(c) -> userService.updateUser(
               userI
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
