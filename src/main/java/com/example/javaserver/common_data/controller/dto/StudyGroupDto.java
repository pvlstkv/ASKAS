package com.example.javaserver.common_data.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;

public class StudyGroupDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private Integer code;

    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private Integer groupNumber;

    @NotNull
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private Integer numberOfSemester;

    @Size(min = 1, max = 50)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String shortName;

    @Size(max = 50)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String fullName;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private OffsetDateTime createdAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private OffsetDateTime updatedAt;

    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private Integer yearOfStudyStart;

    @NotNull
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private Long departmentId;

    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private List<Integer> studentIds;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Set<Long> subjectSemesterIds;

    public StudyGroupDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(Integer groupNumber) {
        this.groupNumber = groupNumber;
    }

    public Integer getNumberOfSemester() {
        return numberOfSemester;
    }

    public void setNumberOfSemester(Integer numberOfSemester) {
        this.numberOfSemester = numberOfSemester;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public Integer getYearOfStudyStart() {
        return yearOfStudyStart;
    }

    public void setYearOfStudyStart(Integer yearOfStudyStart) {
        this.yearOfStudyStart = yearOfStudyStart;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public List<Integer> getStudentIds() {
        return studentIds;
    }

    public void setStudentIds(List<Integer> studentIds) {
        this.studentIds = studentIds;
    }

    public Set<Long> getSubjectSemesterIds() {
        return subjectSemesterIds;
    }

    public void setSubjectSemesterIds(Set<Long> subjectSemesterIds) {
        this.subjectSemesterIds = subjectSemesterIds;
    }
}
