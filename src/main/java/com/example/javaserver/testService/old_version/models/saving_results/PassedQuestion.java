package com.example.javaserver.testService.models.saving_results;

import com.example.javaserver.testService.models.AnswerChoice;
import com.example.javaserver.testService.models.Question;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
public class PassedQuestion implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne
    private Question question;
    @OneToMany(mappedBy = "passed_question", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AnswerChoice> userAnswers;

    public PassedQuestion(Integer id, Question question, Set<AnswerChoice> userAnswers) {
        this.id = id;
        this.question = question;
        this.userAnswers = userAnswers;
    }

    public PassedQuestion(Question question, Set<AnswerChoice> userAnswers) {
        this.question = question;
        this.userAnswers = userAnswers;
    }

    public PassedQuestion() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Set<AnswerChoice> getUserAnswers() {
        return userAnswers;
    }

    public void setUserAnswers(Set<AnswerChoice> userAnswers) {
        this.userAnswers = userAnswers;
    }
}
