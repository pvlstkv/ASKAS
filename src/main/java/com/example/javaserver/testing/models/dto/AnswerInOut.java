package com.example.javaserver.testing.models.dto;

import java.util.List;
// for checking answers
public class AnswerInOut {
    private Long questionId;
    private List<String> answers;

    public AnswerInOut(Long questionId, List<String> answers) {
        this.questionId = questionId;
        this.answers = answers;
    }

    public AnswerInOut() {
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }
}
