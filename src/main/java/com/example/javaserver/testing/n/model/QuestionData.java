package com.example.javaserver.testing.n.model;

import com.example.javaserver.common_data.model.Subject;
import com.example.javaserver.study.model.UserFile;
import com.example.javaserver.testing.config.QuestionType;
import com.example.javaserver.testing.model.Theme;
import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "question_discriminator",
        discriminatorType = DiscriminatorType.STRING)

public class QuestionData {
    @Id
    @GeneratedValue()
    private Long id;
    //    @Column(columnDefinition = "TEXT") todo @Lob or something not like varchar(255)
    private String question;
    // todo like the required annotation like the question field
    private QuestionType questionType;
    //    @JsonIgnore
    private Double complexity;
    // todo how to do

    @JsonProperty("fileIds")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserFile> userFiles;

    @ManyToOne
    @JsonIgnore
    private Theme theme;

    @ManyToOne
    @JsonIgnore
    private Subject subject;


    public QuestionData(QuestionData questionData) {
        this.id = questionData.getId();
        this.question = questionData.getQuestion();
        this.questionType = questionData.getQuestionType();
        this.complexity = questionData.getComplexity();
        this.userFiles = questionData.getUserFiles();
        this.theme = questionData.getTheme();
        this.subject = questionData.getSubject();
    }

    public QuestionData(String question, QuestionType questionType, Double complexity, Set<UserFile> userFiles, Theme theme, Subject subject) {
        this.question = question;
        this.questionType = questionType;
        this.complexity = complexity;
        this.userFiles = userFiles;
        this.theme = theme;
        this.subject = subject;
    }

    public QuestionData(Long id, String question, QuestionType questionType, Double complexity, Set<UserFile> userFiles, Theme theme, Subject subject) {
        this.id = id;
        this.question = question;
        this.questionType = questionType;
        this.complexity = complexity;
        this.userFiles = userFiles;
        this.theme = theme;
        this.subject = subject;
    }

    public QuestionData() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public Double getComplexity() {
        return complexity;
    }

    public void setComplexity(Double complexity) {
        this.complexity = complexity;
    }

    public Set<UserFile> getUserFiles() {
        return userFiles;
    }

    public void setUserFiles(Set<UserFile> userFiles) {
        this.userFiles = userFiles;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
