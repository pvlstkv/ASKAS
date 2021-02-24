package com.example.javaserver.testService.models;

public class UserAnswer {
    private String answer;
    private boolean isRight;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Boolean getRight() {
        return isRight;
    }

    public void setRight(Boolean right) {
        isRight = right;
    }

    public UserAnswer(String answer, Boolean isRight) {
        this.answer = answer;
        this.isRight = isRight;
    }
}
