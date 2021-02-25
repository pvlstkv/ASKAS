package com.example.javaserver.model.common_data;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Set;

@SuppressWarnings("unused")
@Entity
@Data
@Table(name = "departments")
public class Department implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String shortName;

    private String fullName;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    Faculty faculty;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<StudyGroup> studyGroups;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<Subject> subjects;

    public Department() { }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Set<StudyGroup> getStudyGroups() {
        return studyGroups;
    }

    public void setStudyGroups(Set<StudyGroup> studyGroups) {
        this.studyGroups = studyGroups;
    }

    public Set<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<Subject> subjects) {
        this.subjects = subjects;
    }
}
