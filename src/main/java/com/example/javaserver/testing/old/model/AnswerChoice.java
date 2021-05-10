package com.example.javaserver.testing.old.model;

import com.example.javaserver.study.model.UserFile;
import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "answer_choices")
public class AnswerChoice implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String answer;
    private Boolean isRight;

    @ManyToOne
    @JsonIgnore
    private Question question;

    public AnswerChoice() {
    }

    public AnswerChoice(String answer, Boolean isRight) {
        this.answer = answer;
        this.isRight = isRight;
    }

    public AnswerChoice(String answer, Boolean isRight, Question question) {
        this.answer = answer;
        this.isRight = isRight;
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Boolean getRight() {
        return isRight;
    }

    public void setRight(Boolean isRight) {
        this.isRight = isRight;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public AnswerChoice(String answer) {
        this.answer = answer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}

