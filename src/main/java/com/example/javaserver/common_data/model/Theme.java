package com.example.javaserver.common_data.model;

import com.example.javaserver.testService.new_version.models.Question;
import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.Set;

@SuppressWarnings("unused")
@Entity

@Table(name = "themes")
public class Theme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String decryption;

    @JsonProperty("subjectId")
    @JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
    @JsonIdentityReference(alwaysAsId=true)
    @ManyToOne()
    private Subject subject;


    @OneToMany(mappedBy = "theme", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Question> questions;


    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    public Theme() {
    }

    public Theme(String name) {
        this.name = name;
    }

    public Theme(Long id, String name, String decryption, Subject subject, Set<Question> questions, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.decryption = decryption;
        this.subject = subject;
        this.questions = questions;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public String getDecryption() {
        return decryption;
    }

    public void setDecryption(String decryption) {
        this.decryption = decryption;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Set<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
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
}
