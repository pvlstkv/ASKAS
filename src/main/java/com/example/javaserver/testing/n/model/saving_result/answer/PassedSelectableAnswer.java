package com.example.javaserver.testing.n.model.saving_result.answer;

import com.example.javaserver.testing.n.model.answer.AnswerOption;
import com.example.javaserver.testing.n.model.saving_result.question.PassedSelectableQuestion;

import javax.persistence.*;

@Entity
public class PassedSelectableAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private AnswerOption userAnswer;
    @ManyToOne
    private PassedSelectableQuestion passedSelectableQuestion;

    public PassedSelectableAnswer() {
    }

    public PassedSelectableAnswer(AnswerOption userAnswer, PassedSelectableQuestion passedSelectableQuestion) {
        this.userAnswer = userAnswer;
        this.passedSelectableQuestion = passedSelectableQuestion;
    }

    public PassedSelectableAnswer(Long id, AnswerOption userAnswer, PassedSelectableQuestion passedSelectableQuestion) {
        this.id = id;
        this.userAnswer = userAnswer;
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
}
