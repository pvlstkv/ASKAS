package com.example.javaserver.conference.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

public class ConferenceDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String token;

    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private Set<Long> studyGroupIds;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer userId;

    public ConferenceDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Set<Long> getStudyGroupIds() {
        return studyGroupIds;
    }

    public void setStudyGroupIds(Set<Long> studyGroupIds) {
        this.studyGroupIds = studyGroupIds;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
