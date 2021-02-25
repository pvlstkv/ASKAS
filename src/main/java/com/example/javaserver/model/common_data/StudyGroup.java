package com.example.javaserver.model.common_data;

import com.example.javaserver.model.User;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
@Entity
@Data
@Table(name = "study_groups")
public class StudyGroup implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer code;

    private Integer groupNumber;

    private String shortName;

    private String fullName;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    private Integer yearOfStudyStart;

    @ManyToOne(fetch = FetchType.LAZY)
    Department department;

    @OneToMany(mappedBy = "studyGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    List<User> students = new ArrayList<>();

    public StudyGroup() { }

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
}
