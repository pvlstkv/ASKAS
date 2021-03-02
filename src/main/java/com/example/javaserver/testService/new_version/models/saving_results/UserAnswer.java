package com.example.javaserver.testService.new_version.models.saving_results;

import com.example.javaserver.testService.new_version.models.AnswerChoice;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user_answers")
public class UserAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private AnswerChoice userAnswers;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "passed_question_id")
    private PassedQuestion passedQuestion;

    public UserAnswer() {
    }

    public UserAnswer(Long id, AnswerChoice userAnswers, PassedQuestion passedQuestion) {
        this.id = id;
        this.userAnswers = userAnswers;
        this.passedQuestion = passedQuestion;
    }

    public UserAnswer(AnswerChoice userAnswers, PassedQuestion passedQuestion) {
        this.userAnswers = userAnswers;
        this.passedQuestion = passedQuestion;
    }

    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public AnswerChoice getUserAnswers() {
        return userAnswers;
    }

    public void setUserAnswers(AnswerChoice userAnswers) {
        this.userAnswers = userAnswers;
    }

    public PassedQuestion getPassedQuestion() {
        return passedQuestion;
    }

    public void setPassedQuestion(PassedQuestion passedQuestion) {
        this.passedQuestion = passedQuestion;
    }
}
