package com.example.javaserver.study.controller.dto;

import com.example.javaserver.common_data.model.Mark;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.OffsetDateTime;
import java.util.Set;

public class WorkDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotNull(message = "Работа должна быть прикреплена к заданию")
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private Long taskId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer userId;

    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private Set<Long> fileIds;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private OffsetDateTime createdAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private OffsetDateTime updatedAt;

    @Size(max = 1_000_000)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String teacherComment;

    @Size(max = 1_000_000)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String studentComment;

    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private Mark mark;

    public WorkDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Set<Long> getFileIds() {
        return fileIds;
    }

    public void setFileIds(Set<Long> fileIds) {
        this.fileIds = fileIds;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getTeacherComment() {
        return teacherComment;
    }

    public void setTeacherComment(String teacherComment) {
        this.teacherComment = teacherComment;
    }

    public String getStudentComment() {
        return studentComment;
    }

    public void setStudentComment(String studentComment) {
        this.studentComment = studentComment;
    }

    public Mark getMark() {
        return mark;
    }

    public void setMark(Mark mark) {
        this.mark = mark;
    }
}
