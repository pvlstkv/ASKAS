package com.example.javaserver.common_data.controller.client_model;

import com.example.javaserver.common_data.model.StudyGroup;
import com.example.javaserver.user.client_model.UserIO;

import java.util.Set;
import java.util.stream.Collectors;

public class StudyGroupIO {
    private Integer code;

    private Integer groupNumber;

    private Integer courseNumber;

    private String shortName;

    private String fullName;

    private Integer yearOfStudyStart;

    private String nameDepartment;
    
    private Set<UserIO> Students;

    public StudyGroupIO() {
    }
    public StudyGroupIO(StudyGroup studyGroup) {
        this.code = studyGroup.getCode();
        this.groupNumber = studyGroup.getGroupNumber();
        this.courseNumber = studyGroup.getCourseNumber();
        this.shortName = studyGroup.getShortName();
        this.fullName = studyGroup.getFullName();
        this.yearOfStudyStart = studyGroup.getYearOfStudyStart();
        if(studyGroup.getDepartment() != null){
            this.nameDepartment = studyGroup.getDepartment().getShortName();
        }
        this.Students = studyGroup.getStudents().stream().map(user -> new UserIO(user)).collect(Collectors.toSet());
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

    public Integer getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(Integer courseNumber) {
        this.courseNumber = courseNumber;
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

    public String getNameDepartment() {
        return nameDepartment;
    }

    public void setNameDepartment(String nameDepartment) {
        this.nameDepartment = nameDepartment;
    }

    public Set<UserIO> getStudents() {
        return Students;
    }

    public void setStudents(Set<UserIO> students) {
        this.Students = students;
    }
}
