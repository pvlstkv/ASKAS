package com.example.javaserver.testing.new_v.dto.answer.for_test.result;

public class UserSelectableAnswer {
    private Long answerId;
    private Boolean isRight;

    public UserSelectableAnswer() {
    }

    public UserSelectableAnswer(Long answerId, Boolean isRight) {
        this.answerId = answerId;
        this.isRight = isRight;
    }

    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
    }

    public Boolean getRight() {
        return isRight;
    }

    public void setRight(Boolean right) {
        isRight = right;
    }
}
