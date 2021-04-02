package com.example.javaserver.user.controller;

import com.example.javaserver.general.model.Message;
import com.example.javaserver.general.model.UserDetailsImp;
import com.example.javaserver.general.service.RequestHandlerService;
import com.example.javaserver.user.client_model.UserI;
import com.example.javaserver.user.dto.UpdateUser;
import com.example.javaserver.user.model.User;
import com.example.javaserver.user.model.UserRole;
import com.example.javaserver.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

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

    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    @GetMapping
    public User getUserInfo(
            @RequestParam(required = false) Integer id,
            @AuthenticationPrincipal UserDetailsImp userDetails
            ){
        return  userService.getUser(userDetails,id);
    }

    @ResponseStatus(HttpStatus.OK)
    @Secured({"TEACHER", "ADMIN"})
    @GetMapping("/list")
    public List<User> getUserList(
    ){
        return userService.getListUser();
    }

    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    @GetMapping("/search-by")
    public List<User> searchByIds(
            @RequestParam("id") Set<Integer> ids
    ) {
        return userService.searchByIds(ids);
    }

    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    @PutMapping
    public Message putUser(

            @RequestBody UpdateUser updateUser,
            @AuthenticationPrincipal UserDetailsImp userDetails
    ){
        return userService.putUser(userDetails,updateUser);
    }

    @ResponseStatus(HttpStatus.OK)
    @Secured({"ADMIN"})
    @PatchMapping
    public Message patchUser(
            @RequestBody UserI userI


            ){
        return userService.updateUser(userI);
    }

    @ResponseStatus(HttpStatus.OK)
    @Secured({"ADMIN"})
    @DeleteMapping
    public Message deleteUser(
            @RequestParam Integer id
    ){
        return userService.deleteUser(id);
    }



}
