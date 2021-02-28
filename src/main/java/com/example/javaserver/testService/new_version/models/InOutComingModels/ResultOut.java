package com.example.javaserver.testService.new_version.models.InOutComingModels;


import java.util.List;

public class ResultOut {
    private List<QuestionOut> questions;
    private Long rating;

    public ResultOut(List<QuestionOut> questions, Long rating) {
        this.questions = questions;
        this.rating = rating;
    }

    public List<QuestionOut> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionOut> questions) {
        this.questions = questions;
    }

    public Long getRating() {
        return rating;
    }

    public void setRating(Long rating) {
        this.rating = rating;
    }
}
