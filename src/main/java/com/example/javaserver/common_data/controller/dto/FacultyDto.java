package com.example.javaserver.common_data.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

public class FacultyDto {
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @Size(min = 1, max = 50)
    public String shortName;

    @Size(min = 1, max = 50)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    public String fullName;

    @NotNull
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private Set<Long> departmentIds;

    public FacultyDto() { }

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

    public Set<Long> getDepartmentIds() {
        return departmentIds;
    }

    public void setDepartmentIds(Set<Long> departmentIds) {
        this.departmentIds = departmentIds;
    }
}
