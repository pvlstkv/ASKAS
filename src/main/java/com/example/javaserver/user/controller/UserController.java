package com.example.javaserver.user.controller;

import com.example.javaserver.general.model.Message;
import com.example.javaserver.general.model.UserDetailsImp;
import com.example.javaserver.user.controller.dto.UserDto;
import com.example.javaserver.user.controller.dto.UpdateUser;
import com.example.javaserver.user.controller.mapper.UserMapper;
import com.example.javaserver.user.model.User;
import com.example.javaserver.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    @GetMapping
    public UserDto getUserInfo(
            @RequestParam(required = false) Integer id,
            @AuthenticationPrincipal UserDetailsImp userDetails
            ){
        return  userMapper.toDto(
                userService.getUser(userDetails,id)
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @Secured({"TEACHER", "ADMIN"})
    @GetMapping("/list")
    public Collection<UserDto> getUserList(
    ){
        return userMapper.toDto(
                userService.getListUser()
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    @GetMapping("/search-by-ids")
    public Collection<UserDto> searchByIds(
            @RequestParam("ids") Set<Integer> ids
    ) {
        return userMapper.toDto(
                userService.getByIds(ids)
        );
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
    @PutMapping("/edit")
    public Message patchUser(
            @RequestBody UserDto userDto

            ){
        return userService.updateUser(userDto);
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
