package com.example.javaserver.study.controller.dto;

import com.example.javaserver.user.model.UserRole;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@SuppressWarnings("unused")
public class UserFileDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank
    @Size(min = 1, max = 50)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String name;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String contentType;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long contentLength;

    @NotNull
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private UserRole accessLevel;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer linkCount;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer userId;

    public UserFileDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Long getContentLength() {
        return contentLength;
    }

    public void setContentLength(Long contentLength) {
        this.contentLength = contentLength;
    }

    public UserRole getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(UserRole accessLevel) {
        this.accessLevel = accessLevel;
    }

    public Integer getLinkCount() {
        return linkCount;
    }

    public void setLinkCount(Integer linkCount) {
        this.linkCount = linkCount;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
