package com.example.javaserver.testing.new_v.model.saving_result.answer;

import com.example.javaserver.testing.new_v.model.answer.AnswerOption;
import com.example.javaserver.testing.new_v.model.saving_result.question.PassedSelectableQuestion;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class PassedSelectableAnswer implements Serializable, Answerable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private AnswerOption userAnswer;

    private Boolean isRight;
    @JsonIgnore
    @ManyToOne
    private PassedSelectableQuestion passedSelectableQuestion;

    public PassedSelectableAnswer() {
    }

    public PassedSelectableAnswer(AnswerOption userAnswer, Boolean isRight, PassedSelectableQuestion passedSelectableQuestion) {
        this.userAnswer = userAnswer;
        this.isRight = isRight;
        this.passedSelectableQuestion = passedSelectableQuestion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AnswerOption getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(AnswerOption userAnswer) {
        this.userAnswer = userAnswer;
    }

    public PassedSelectableQuestion getPassedSelectableQuestion() {
        return passedSelectableQuestion;
    }

    public void setPassedSelectableQuestion(PassedSelectableQuestion passedSelectableQuestion) {
        this.passedSelectableQuestion = passedSelectableQuestion;
    }

    public Boolean getRight() {
        return isRight;
    }

    public void setRight(Boolean right) {
        isRight = right;
    }
}
