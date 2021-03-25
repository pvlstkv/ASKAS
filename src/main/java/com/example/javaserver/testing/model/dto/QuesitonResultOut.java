package com.example.javaserver.testing.model.dto;

import java.util.List;

public class QuesitonResultOut {
    private Long questionId;
    private List<String> rightAnswers;

    public QuesitonResultOut(Long questionId, List<String> rightAnswers) {
        this.questionId = questionId;
        this.rightAnswers = rightAnswers;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public List<String> getRightAnswers() {
        return rightAnswers;
    }

    public void setRightAnswers(List<String> rightAnswers) {
        this.rightAnswers = rightAnswers;
    }
}
