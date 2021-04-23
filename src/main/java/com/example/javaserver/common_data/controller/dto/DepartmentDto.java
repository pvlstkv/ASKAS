package com.example.javaserver.common_data.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

public class DepartmentDto {

    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @NotNull
    @NotEmpty
    @Size(min = 1, max = 50)
    public String shortName;

    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @NotNull
    @NotEmpty
    @Size(min = 1, max = 50)
    public String fullName;


    @NotNull
    public Long facultyId;

    public DepartmentDto() {
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

    public Long getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(Long facultyId) {
        this.facultyId = facultyId;
    }
}
