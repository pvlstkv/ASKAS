package com.example.javaserver.testing.n.model.saving_result.answer;

import com.example.javaserver.testing.n.model.saving_result.question.PassedWriteableQuestion;

import javax.persistence.*;

@Entity
public class PassedWriteableAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userAnswer;

    @ManyToOne(cascade = CascadeType.ALL)
    private PassedWriteableQuestion passedWriteableQuestion;


    public PassedWriteableAnswer() {

    }

    public PassedWriteableAnswer(String userAnswer, PassedWriteableQuestion passedWriteableQuestion) {
        this.userAnswer = userAnswer;
        this.passedWriteableQuestion = passedWriteableQuestion;
    }

    public PassedWriteableAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public PassedWriteableQuestion getPassedWriteableQuestion() {
        return passedWriteableQuestion;
    }

    public void setPassedWriteableQuestion(PassedWriteableQuestion passedWriteableQuestion) {
        this.passedWriteableQuestion = passedWriteableQuestion;
    }
}
