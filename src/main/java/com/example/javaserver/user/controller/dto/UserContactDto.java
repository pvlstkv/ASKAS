package com.example.javaserver.user.controller.dto;

import com.example.javaserver.user.model.UserContactType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserContactDto {
    private Long id;

    @NotNull
    private Integer userId;

    @NotNull
    private UserContactType type;

    @Size(min = 1, max = 255)
    private String value;

    public UserContactDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public UserContactType getType() {
        return type;
    }

    public void setType(UserContactType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
