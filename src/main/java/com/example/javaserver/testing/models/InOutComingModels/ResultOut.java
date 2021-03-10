package com.example.javaserver.testing.models.InOutComingModels;


import java.util.List;

public class ResultOut {
    private List<AnswerInOut> answerInOuts;
    private Integer ratingInPercent;

    public ResultOut(List<AnswerInOut> answerInOuts, Integer ratingInPercent) {
        this.answerInOuts = answerInOuts;
        this.ratingInPercent = ratingInPercent;
    }

    public List<AnswerInOut> getQuestions() {
        return answerInOuts;
    }

    public void setQuestions(List<AnswerInOut> answerInOuts) {
        this.answerInOuts = answerInOuts;
    }

    public Integer getRating() {
        return ratingInPercent;
    }

    public void setRating(Integer ratingInPercent) {
        this.ratingInPercent = ratingInPercent;
    }
}
