package com.example.javaserver.testService.new_version.models.InOutComingModels;

import java.util.List;
// for checking answers
public class AnswerInOut {
    private Integer questionId;
    private List<String> answers;

    public AnswerInOut(Integer questionId, List<String> answers) {
        this.questionId = questionId;
        this.answers = answers;
    }

    public AnswerInOut() {
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
