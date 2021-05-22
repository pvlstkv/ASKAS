package com.example.javaserver.testing.new_v.service.model;

import com.example.javaserver.testing.new_v.model.question.QuestionData;
import com.example.javaserver.testing.new_v.model.saving_result.question.PassedQuestionData;

public class CheckQuestion {
    private double rating;
    private QuestionData originalQuestion;
    private Object userAnswers;
    private PassedQuestionData passedQuestion;

    public CheckQuestion() {
    }

    public CheckQuestion(QuestionData originalQuestion, Object userAnswers, PassedQuestionData passedQuestion) {
        this.rating = rating;
        this.originalQuestion = originalQuestion;
        this.userAnswers = userAnswers;
        this.passedQuestion = passedQuestion;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public QuestionData getOriginalQuestion() {
        return originalQuestion;
    }

    public void setOriginalQuestion(QuestionData originalQuestion) {
        this.originalQuestion = originalQuestion;
    }

    public Object getUserAnswers() {
        return userAnswers;
    }

    public void setUserAnswers(Object userAnswers) {
        this.userAnswers = userAnswers;
    }

    public PassedQuestionData getPassedQuestion() {
        return passedQuestion;
    }

    public void setPassedQuestion(PassedQuestionData passedQuestion) {
        this.passedQuestion = passedQuestion;
    }
}
