package com.example.javaserver.model.commonData;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String name;
    private String decryption;
    private int countOfSemesters;
    // temporary crutch
    private int numberOfSemester;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubjectSemester> semesters = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Department department;

    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    public Subject(long id, String name, String decryption, int countOfSemesters, int numberOfSemester, List<SubjectSemester> semesters, Department department, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.decryption = decryption;
        this.countOfSemesters = countOfSemesters;
        this.numberOfSemester = numberOfSemester;
        this.semesters = semesters;
        this.department = department;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Subject() {

    }

    public int getNumberOfSemester() {
        return numberOfSemester;
    }

    public void setNumberOfSemester(int numberOfSemester) {
        this.numberOfSemester = numberOfSemester;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDecryption() {
        return decryption;
    }

    public void setDecryption(String decryption) {
        this.decryption = decryption;
    }

    public List<SubjectSemester> getSemesters() {
        return semesters;
    }

    public void setSemesters(List<SubjectSemester> semesters) {
        this.semesters = semesters;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
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

    public int getCountOfSemesters() {
        return countOfSemesters;
    }

    public void setCountOfSemesters(int countOfSemesters) {
        this.countOfSemesters = countOfSemesters;
    }
}
