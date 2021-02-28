package com.example.javaserver.testService.old_version.models;

import javax.persistence.*;

@Entity
public class AnswerChoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String answer;
    private Boolean isRight;

    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "question_id")
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

    public void setRight(Boolean right) {
        isRight = right;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public AnswerChoice(String answer, Question question) {
        this.answer = answer;
//        this.question = question;
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

