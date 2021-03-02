package com.example.javaserver.testService.new_version.services;

import com.example.javaserver.testService.new_version.models.InOutComingModels.AnswerInOut;

public class CheckedQuestion {
    private AnswerInOut answerInOut;
    private Double rating;

    public CheckedQuestion() {
    }

    public CheckedQuestion(AnswerInOut answerInOut, Double rating) {
        this.answerInOut = answerInOut;
        this.rating = rating;
    }

    public AnswerInOut getAnswerInOut() {
        return answerInOut;
    }

    public void setAnswerInOut(AnswerInOut answerInOut) {
        this.answerInOut = answerInOut;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
