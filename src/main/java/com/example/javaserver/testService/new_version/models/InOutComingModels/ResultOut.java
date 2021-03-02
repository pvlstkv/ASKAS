package com.example.javaserver.testService.new_version.models.InOutComingModels;


import java.util.List;

public class ResultOut {
    private List<AnswerInOut> answerInOuts;
    private Long rating;

    public ResultOut(List<AnswerInOut> answerInOuts, Long rating) {
        this.answerInOuts = answerInOuts;
        this.rating = rating;
    }

    public List<AnswerInOut> getQuestions() {
        return answerInOuts;
    }

    public void setQuestions(List<AnswerInOut> answerInOuts) {
        this.answerInOuts = answerInOuts;
    }

    public Long getRating() {
        return rating;
    }

    public void setRating(Long rating) {
        this.rating = rating;
    }
}
