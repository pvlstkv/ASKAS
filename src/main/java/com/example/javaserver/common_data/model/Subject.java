package com.example.javaserver.common_data.model;

//import com.example.javaserver.testService.old_version.models.Question;

import com.example.javaserver.testService.new_version.models.Question;


import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unused")
@Entity

@Table(name = "subjects")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String decryption;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Theme> themes;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SubjectSemester> semesters;

    @ManyToOne(fetch = FetchType.LAZY)
    private Department department;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> question = new ArrayList<>();

    public Subject() {
    }

    public Subject(String name, Set<Theme> themes) {
        this.name = name;
        this.themes = themes;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Theme> getThemes() {
        return themes;
    }

    public void setThemes(Set<Theme> themes) {
        this.themes = themes;
    }

    public String getDecryption() {
        return decryption;
    }

    public void setDecryption(String description) {
        this.decryption = description;
    }

    public List<Question> getQuestion() {
        return question;
    }

    public void setQuestion(List<Question> question) {
        this.question = question;
    }

    public Subject(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Set<SubjectSemester> getSemesters() {
        return semesters;
    }

    public void setSemesters(Set<SubjectSemester> semesters) {
        this.semesters = semesters;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
