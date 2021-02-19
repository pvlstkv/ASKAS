package com.example.javaserver.model.commonData;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Faculty {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    long id;

    String shortName;
    String fullName;
    OffsetDateTime createdAt;
    OffsetDateTime updatedAt;

    @OneToMany(mappedBy = "faculty", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Department> departments = new ArrayList<>();

    public Faculty(long id, String shortName, String fullName, OffsetDateTime createdAt, OffsetDateTime updatedAt, List<Department> departments) {
        this.id = id;
        this.shortName = shortName;
        this.fullName = fullName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.departments = departments;
    }

    public Faculty() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }
    // getters/setters/constructors

}
