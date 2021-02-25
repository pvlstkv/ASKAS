package com.example.javaserver.model.commonData;

import com.example.javaserver.model.User;
import com.example.javaserver.model.commonData.Department;
import com.example.javaserver.model.commonData.SubjectSemester;
import lombok.Data;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unused")
@Entity
@Data
@Table(name = "subjects")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String decryption;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private User createdBy;

//    @OneToMany(mappedBy = "subject")
//    private List<Question> question;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<SubjectSemester> semesters;

    @ManyToOne(fetch = FetchType.LAZY)
    Department department;

    public Subject() { }

    public String getDecryption() {
        return decryption;
    }

    public void setDecryption(String description) {
        this.decryption = description;
    }

//    public List<Question> getQuestion() {
//        return question;
//    }
//
//    public void setQuestion(List<Question> question) {
//        this.question = question;
//    }

    public Subject(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
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
