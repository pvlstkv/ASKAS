package com.example.javaserver.study.controller.dto;

import com.example.javaserver.study.model.LiteratureType;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@SuppressWarnings("unused")
public class LiteratureDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotNull
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private LiteratureType type;

    @Size(min = 1, max = 50)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String title;

    @Size(min = 1, max = 50)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String authors;

    @Size(max = 1_000_000)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String description;

    @NotNull
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private Set<Long> fileIds;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer userId;

    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private Set<Long> semesterIds;

    public LiteratureDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LiteratureType getType() {
        return type;
    }

    public void setType(LiteratureType type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Long> getFileIds() {
        return fileIds;
    }

    public void setFileIds(Set<Long> fileIds) {
        this.fileIds = fileIds;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Set<Long> getSemesterIds() {
        return semesterIds;
    }

    public void setSemesterIds(Set<Long> semesterIds) {
        this.semesterIds = semesterIds;
    }
}
