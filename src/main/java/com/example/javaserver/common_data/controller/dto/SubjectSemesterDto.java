package com.example.javaserver.common_data.controller.dto;

import com.example.javaserver.common_data.model.SubjectControlType;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.Set;

@SuppressWarnings("unused")
public class SubjectSemesterDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String name;

    @NotNull
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private SubjectControlType controlType;

    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private Boolean hasCourseWork;

    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private Boolean hasCourseProject;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Set<Long> taskIds;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Set<Long> literatureIds;

    @NotNull
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private Long subjectId;

    @NotNull
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private Long studyGroupId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private OffsetDateTime createdAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private OffsetDateTime updatedAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Set<Long> semesterMarkIds;

    public SubjectSemesterDto() { }

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

    public SubjectControlType getControlType() {
        return controlType;
    }

    public void setControlType(SubjectControlType controlType) {
        this.controlType = controlType;
    }

    public Boolean getHasCourseWork() {
        return hasCourseWork;
    }

    public void setHasCourseWork(Boolean hasCourseWork) {
        this.hasCourseWork = hasCourseWork;
    }

    public Boolean getHasCourseProject() {
        return hasCourseProject;
    }

    public void setHasCourseProject(Boolean hasCourseProject) {
        this.hasCourseProject = hasCourseProject;
    }

    public Set<Long> getTaskIds() {
        return taskIds;
    }

    public void setTaskIds(Set<Long> taskIds) {
        this.taskIds = taskIds;
    }

    public Set<Long> getLiteratureIds() {
        return literatureIds;
    }

    public void setLiteratureIds(Set<Long> literatureIds) {
        this.literatureIds = literatureIds;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public Long getStudyGroupId() {
        return studyGroupId;
    }

    public void setStudyGroupId(Long studyGroupId) {
        this.studyGroupId = studyGroupId;
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

    public Set<Long> getSemesterMarkIds() {
        return semesterMarkIds;
    }

    public void setSemesterMarkIds(Set<Long> semesterMarkIds) {
        this.semesterMarkIds = semesterMarkIds;
    }
}
