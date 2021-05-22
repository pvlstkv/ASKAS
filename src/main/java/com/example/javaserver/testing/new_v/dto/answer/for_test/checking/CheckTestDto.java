package com.example.javaserver.testing.new_v.dto.answer.for_test.checking;

public class CheckTestDto {
    private Long questionId;
    private Object answers;

    public CheckTestDto() {
    }

    public CheckTestDto(Long questionId, Object answers) {
        this.questionId = questionId;
        this.answers = answers;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Object getAnswers() {
        return answers;
    }

    public void setAnswers(Object answers) {
        this.answers = answers;
    }
}
