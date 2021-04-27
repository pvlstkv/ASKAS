package com.example.javaserver.testing.new_v.dto.answer.for_test.result;

public class UserWriteableAnswer {
    private String userAnswer;
    private Boolean isRight;

    public UserWriteableAnswer() {
    }

    public UserWriteableAnswer(String userAnswer, Boolean isRight) {
        this.userAnswer = userAnswer;
        this.isRight = isRight;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public Boolean getRight() {
        return isRight;
    }

    public void setRight(Boolean right) {
        isRight = right;
    }
}
