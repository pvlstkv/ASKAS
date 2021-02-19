package com.example.javaserver.model.commonData;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String shortName;
    private String fullName;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private Faculty faculty;
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StudyGroup> studyGroups = new ArrayList<>();
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Subject> subjects = new ArrayList<>();

    public Department(long id, String shortName, String fullName, OffsetDateTime createdAt, OffsetDateTime updatedAt, Faculty faculty, List<StudyGroup> studyGroups, List<Subject> subjects) {
        this.id = id;
        this.shortName = shortName;
        this.fullName = fullName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.faculty = faculty;
        this.studyGroups = studyGroups;
        this.subjects = subjects;
    }

    public Department() {

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

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public List<StudyGroup> getStudyGroups() {
        return studyGroups;
    }

    public void setStudyGroups(List<StudyGroup> studyGroups) {
        this.studyGroups = studyGroups;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }
}
