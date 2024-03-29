package com.example.javaserver.testing.new_v.model.saving_result.answer;

import com.example.javaserver.testing.new_v.model.answer.AnswerOption;
import com.example.javaserver.testing.new_v.model.saving_result.question.PassedMatchableQuestion;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class PassedMatchableAnswer implements Serializable, Answerable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private AnswerOption key;

    @OneToOne
    private AnswerOption value;

    private Boolean isRight;

    @JsonIgnore
    @ManyToOne
    private PassedMatchableQuestion passedMatchableQuestion;

    public PassedMatchableAnswer() {
    }

    public PassedMatchableAnswer(Long id, AnswerOption key, AnswerOption value, Boolean isRight, PassedMatchableQuestion passedMatchableQuestion) {
        this.id = id;
        this.key = key;
        this.value = value;
        this.isRight = isRight;
        this.passedMatchableQuestion = passedMatchableQuestion;
    }

    public PassedMatchableAnswer(AnswerOption key, AnswerOption value, Boolean isRight, PassedMatchableQuestion passedMatchableQuestion) {
        this.key = key;
        this.value = value;
        this.isRight = isRight;
        this.passedMatchableQuestion = passedMatchableQuestion;
    }
    public PassedMatchableAnswer(AnswerOption key, AnswerOption value, Boolean isRight) {
        this.key = key;
        this.value = value;
        this.isRight = isRight;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AnswerOption getKey() {
        return key;
    }

    public void setKey(AnswerOption key) {
        this.key = key;
    }

    public AnswerOption getValue() {
        return value;
    }

    public void setValue(AnswerOption value) {
        this.value = value;
    }

    public PassedMatchableQuestion getPassedMatchableQuestion() {
        return passedMatchableQuestion;
    }

    public void setPassedMatchableQuestion(PassedMatchableQuestion passedMatchableQuestion) {
        this.passedMatchableQuestion = passedMatchableQuestion;
    }

    public Boolean getRight() {
        return isRight;
    }

    public void setRight(Boolean right) {
        isRight = right;
    }
}
