package com.example.javaserver.testing.new_v.dto.answer.for_test.result;

public class UserMatchableAnswer {
    private Long key;
    private Long value;
    private Boolean isRight;


    public UserMatchableAnswer(Long key, Long value, Boolean isRight) {
        this.key = key;
        this.value = value;
        this.isRight = isRight;
    }

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public Boolean getRight() {
        return isRight;
    }

    public void setRight(Boolean right) {
        isRight = right;
    }

}
