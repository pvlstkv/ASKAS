package com.example.javaserver.common_data.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unused")
public class SubjectDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @Size(min = 1, max = 50)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String name;

    @Size(max = 1_000_000)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String decryption;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private OffsetDateTime createdAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private OffsetDateTime updatedAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Set<Long> themeIds;

    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private Set<Integer> teacherIds;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Set<Long> semesterIds;

    @NotNull
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private Long departmentId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<Long> questionIds;

    public SubjectDto() { }

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

    public String getDecryption() {
        return decryption;
    }

    public void setDecryption(String decryption) {
        this.decryption = decryption;
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

    public Set<Long> getThemeIds() {
        return themeIds;
    }

    public void setThemeIds(Set<Long> themeIds) {
        this.themeIds = themeIds;
    }

    public Set<Integer> getTeacherIds() {
        return teacherIds;
    }

    public void setTeacherIds(Set<Integer> teacherIds) {
        this.teacherIds = teacherIds;
    }

    public Set<Long> getSemesterIds() {
        return semesterIds;
    }

    public void setSemesterIds(Set<Long> semesterIds) {
        this.semesterIds = semesterIds;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public List<Long> getQuestionIds() {
        return questionIds;
    }

    public void setQuestionIds(List<Long> questionIds) {
        this.questionIds = questionIds;
    }
}
