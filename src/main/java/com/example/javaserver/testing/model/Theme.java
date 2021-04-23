package com.example.javaserver.testing.model;

import com.example.javaserver.common_data.model.Subject;
import com.example.javaserver.testing.model.dto.ThemeIn;
import com.example.javaserver.testing.model.dto.ThemeUpdateIn;

import com.example.javaserver.testing.n.model.QuestionData;
import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Set;

@SuppressWarnings("unused")
@Entity

@Table(name = "themes")
public class Theme implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String decryption;

    @JsonProperty("subjectId")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne
    private Subject subject;

//    @JsonProperty("passedTestIds")
//    @JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
//    @JsonIdentityReference(alwaysAsId=true)
//    @OneToMany(mappedBy = "theme", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonIgnore
//    private Set<PassedTest> passedTests;


    @OneToMany(mappedBy = "theme", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<QuestionData> questions;

    private Integer questionQuantityInTest;

    private Integer attemptNumberInTest;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    public Theme() {
    }

    public Theme(String name) {
        this.name = name;
    }

    public Theme(Long id) {
        this.id = id;
    }

    public Theme(Long id, String name, String decryption, Subject subject, Set<QuestionData> questions, Integer questionQuantityInTest, Integer attemptNumberInTest, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.decryption = decryption;
        this.subject = subject;
        this.questions = questions;
        this.questionQuantityInTest = questionQuantityInTest;
        this.attemptNumberInTest = attemptNumberInTest;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Theme(ThemeIn themeIn, Subject subject) {
        this.name = themeIn.getName();
        this.decryption = themeIn.getDecryption();
        this.attemptNumberInTest = themeIn.getAttemptNumberInTest();
        this.questionQuantityInTest = themeIn.getQuestionQuantityInTest();
        this.subject = subject;
    }

    public Theme(ThemeUpdateIn themeUpdateIn, Subject subject) {
        this.subject = subject;
        this.name = themeUpdateIn.getName();
        this.decryption = themeUpdateIn.getDecryption();
        this.attemptNumberInTest = themeUpdateIn.getAttemptNumberInTest();
        this.questionQuantityInTest = themeUpdateIn.getQuestionQuantityInTest();
        this.id = themeUpdateIn.getId();
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

    public Set<QuestionData> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<QuestionData> questions) {
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

    public Integer getQuestionQuantityInTest() {
        return questionQuantityInTest;
    }

    public void setQuestionQuantityInTest(Integer questionQuantityInTest) {
        this.questionQuantityInTest = questionQuantityInTest;
    }

    public Integer getAttemptNumberInTest() {
        return attemptNumberInTest;
    }

    public void setAttemptNumberInTest(Integer attemptNumberInTest) {
        this.attemptNumberInTest = attemptNumberInTest;
    }
}
