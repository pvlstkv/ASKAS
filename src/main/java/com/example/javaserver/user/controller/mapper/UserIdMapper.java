package com.example.javaserver.user.controller.mapper;

import com.example.javaserver.user.model.User;
import com.example.javaserver.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
@Component
public class UserIdMapper {
    private final UserService service;

    @Autowired
    public UserIdMapper(UserService service) {
        this.service = service;
    }

    public User getById(Integer id) {
        return id == null
                ? null
                : service.getById(id);
    }

    public Integer getId(User user) {
        return user == null
                ? null
                : user.getId();
    }

    public Set<User> getByIds(Set<Integer> ids) {
        return ids == null
                ? null
                : service.getByIds(ids);
    }

    public Set<Integer> getIds(Set<User> users) {
        return users == null
                ? null
                : users.stream().map(User::getId).collect(Collectors.toSet());
    }
}
