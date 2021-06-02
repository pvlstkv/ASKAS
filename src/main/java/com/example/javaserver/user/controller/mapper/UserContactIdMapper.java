package com.example.javaserver.user.controller.mapper;

import com.example.javaserver.user.model.User;
import com.example.javaserver.user.model.UserContact;
import com.example.javaserver.user.service.UserContactService;
import com.example.javaserver.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserContactIdMapper {
    private final UserContactService service;

    @Autowired
    public UserContactIdMapper(UserContactService service) {
        this.service = service;
    }

    public UserContact getById(Long id) {
        return id == null
                ? null
                : service.getById(id);
    }

    public Long getId(UserContact userContact) {
        return userContact == null
                ? null
                : userContact.getId();
    }

    public Set<UserContact> getByIds(Set<Long> ids) {
        return ids == null
                ? null
                : service.getByIds(ids);
    }

    public Set<Long> getIds(Set<UserContact> userContacts) {
        return userContacts == null
                ? null
                : userContacts.stream().map(UserContact::getId).collect(Collectors.toSet());
    }
}
