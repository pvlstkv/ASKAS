package com.example.javaserver.common_data.controller.client_model;

import com.example.javaserver.user.controller.dto.UserI;

import java.util.Set;

public class StudyGroupI {

    private Integer code;

    private Integer groupNumber;

    private Integer numberOfSemester;

    private String shortName;

    private String fullName;

    private Integer yearOfStudyStart;

    private Long idDepartment;

    private Set<UserI> Students;

    public StudyGroupI() {
    }

    public void setIdDepartment(Long idDepartment) {
        this.idDepartment = idDepartment;
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

    public Integer getYearOfStudyStart() {
        return yearOfStudyStart;
    }

    public void setYearOfStudyStart(Integer yearOfStudyStart) {
        this.yearOfStudyStart = yearOfStudyStart;
    }

    public Long getIdDepartment() {
        return idDepartment;
    }

    public Set<UserI> getStudents() {
        return Students;
    }

    public void setStudents(Set<UserI> students) {
        this.Students = students;
    }
}
