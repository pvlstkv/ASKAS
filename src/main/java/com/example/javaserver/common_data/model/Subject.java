package com.example.javaserver.common_data.model;


import com.example.javaserver.testing.theme.Theme;
import com.example.javaserver.user.model.User;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.Set;

@SuppressWarnings("unused")
@Entity

@Table(name = "subjects")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String name;

    @Column(length = 1_000_000)
    private String decryption;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Theme> themes;

    @ManyToMany
    @JoinTable(
            name = "teacher_subject",
            joinColumns = {@JoinColumn(name = "subject_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private Set<User> teachers;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SubjectSemester> semesters;

    @ManyToOne
    private Department department;

    @PreRemove
    private void removeHandler() {
        teachers.forEach(u -> u.getTeachingSubjects().remove(this));
    }

    public Subject() {
    }

    public Subject(String name) {
        this.name = name;
    }

    public Subject(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
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

    public void setDecryption(String decryption) {
        this.decryption = decryption;
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

    public Set<User> getTeachers() {
        return teachers;
    }

    public void setTeachers(Set<User> teachers) {
        this.teachers = teachers;
    }
}
