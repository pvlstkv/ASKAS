package com.example.javaserver.study.controller.dto;

import com.example.javaserver.study.model.TaskType;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unused")
public class TaskDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotNull(message = "Задание должно иметь тип")
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private TaskType type;

    @Size(min = 1, max = 50)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String title;

    @Size(max = 1_000_000)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String description;

    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private Set<Long> fileIds;

    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private Set<Long> semesterIds;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<Long> workIds;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer userId;

    public TaskDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TaskType getType() {
        return type;
    }

    public void setType(TaskType type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Set<Long> getSemesterIds() {
        return semesterIds;
    }

    public void setSemesterIds(Set<Long> semesterIds) {
        this.semesterIds = semesterIds;
    }

    public List<Long> getWorkIds() {
        return workIds;
    }

    public void setWorkIds(List<Long> workIds) {
        this.workIds = workIds;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
