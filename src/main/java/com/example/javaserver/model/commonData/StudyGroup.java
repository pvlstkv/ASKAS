package com.example.javaserver.model.commonData;

import com.example.javaserver.model.Consumer;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
//@Table(name = "")
public class StudyGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    long id;

    Integer code;
    Integer groupNumber;
    String shortName;
    String fullName;
    OffsetDateTime createdAt;
    OffsetDateTime updatedAt;
    Integer yearOfStudyStart;

    @ManyToOne(fetch = FetchType.LAZY)
    Department department;
    @OneToMany(mappedBy = "studyGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Consumer> students = new ArrayList<>();

    public StudyGroup(long id, Integer code, Integer groupNumber, String shortName, String fullName, OffsetDateTime createdAt, OffsetDateTime updatedAt, Integer yearOfStudyStart, Department department, List<Consumer> students) {
        this.id = id;
        this.code = code;
        this.groupNumber = groupNumber;
        this.shortName = shortName;
        this.fullName = fullName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.yearOfStudyStart = yearOfStudyStart;
        this.department = department;
        this.students = students;
    }

    public StudyGroup() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public List<Consumer> getStudents() {
        return students;
    }

    public void setStudents(List<Consumer> students) {
        this.students = students;
    }
}
