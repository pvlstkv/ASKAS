package com.example.javaserver.testService.old_version.models.InOutComingModels;

import java.util.List;

public class RequestedAnswer {
    Integer questionId;
    List<String> answers;

    public RequestedAnswer(Integer questionId, List<String> answers) {
        this.questionId = questionId;
        this.answers = answers;
    }

    public RequestedAnswer() {
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }
}
