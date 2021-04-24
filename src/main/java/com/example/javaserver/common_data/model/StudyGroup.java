package com.example.javaserver.common_data.model;

import com.example.javaserver.common_data.controller.dto.StudyGroupDto;
import com.example.javaserver.conference.model.Conference;
import com.example.javaserver.user.model.User;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unused")
@Entity

@Table(name = "study_groups")
public class StudyGroup implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer code;

    private Integer groupNumber;

    private Integer numberOfSemester;

    @Column(length = 50)
    private String shortName;

    @Column(length = 50)
    private String fullName;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    private Integer yearOfStudyStart;

    @ManyToOne(fetch = FetchType.EAGER)
    private Department department;

    @OneToMany(mappedBy = "studyGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> students;

    @OneToMany(mappedBy = "studyGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SubjectSemester> subjectSemesters;

    @OneToMany(mappedBy = "studyGroup")
    private Set<Conference> conferences;

    public StudyGroup() {
    }

    public StudyGroup(Integer code, Integer groupNumber, Integer numberOfSemester, String shortName, String fullName, Integer yearOfStudyStart) {
        this.code = code;
        this.groupNumber = groupNumber;
        this.numberOfSemester = numberOfSemester;
        this.shortName = shortName;
        this.fullName = fullName;
        this.yearOfStudyStart = yearOfStudyStart;
    }

    public Integer getNumberOfSemester() {
        return numberOfSemester;
    }

    public void setNumberOfSemester(Integer numberOfSemester) {
        this.numberOfSemester = numberOfSemester;
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

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<User> getStudents() {
        return students;
    }

    public void setStudents(List<User> students) {
        this.students = students;
    }

    public Set<SubjectSemester> getSubjectSemesters() {
        return subjectSemesters;
    }

    public void setSubjectSemesters(Set<SubjectSemester> subjectSemesters) {
        this.subjectSemesters = subjectSemesters;
    }
}
