package com.example.javaserver.common_data.model;

import com.example.javaserver.common_data.controller.client_model.StudyGroupIO;
import com.example.javaserver.user.model.User;

import javax.persistence.*;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unused")
@Entity

@Table(name = "study_groups")
public class StudyGroup implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer code;

    private Integer groupNumber;

    private Integer courseNumber;

    private String shortName;

    private String fullName;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    private Integer yearOfStudyStart;

    @ManyToOne(fetch = FetchType.LAZY)
    private Department department;

    @OneToMany(mappedBy = "studyGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> students;

    @ManyToMany
    @JoinTable(
            name = "study_groups_subject_semesters",
            joinColumns = {@JoinColumn(name = "study_group_id")},
            inverseJoinColumns = {@JoinColumn(name = "subject_semester_id")})
    private Set<SubjectSemester> subjectSemesters;

    public StudyGroup() { }

    public StudyGroup(StudyGroupIO studyGroupIO) {
        this.code = studyGroupIO.getCode();
        this.groupNumber = studyGroupIO.getGroupNumber();
        this.courseNumber = studyGroupIO.getCourseNumber();
        this.shortName = studyGroupIO.getShortName();
        this.fullName = studyGroupIO.getFullName();
        this.yearOfStudyStart = studyGroupIO.getYearOfStudyStart();
    }


    public StudyGroup(Integer code, Integer groupNumber, Integer courseNumber, String shortName, String fullName, Integer yearOfStudyStart) {
        this.code = code;
        this.groupNumber = groupNumber;
        this.courseNumber = courseNumber;
        this.shortName = shortName;
        this.fullName = fullName;
        this.yearOfStudyStart = yearOfStudyStart;
    }

    public Integer getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(Integer courseNumber) {
        this.courseNumber = courseNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
