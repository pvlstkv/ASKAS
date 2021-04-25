package com.example.javaserver.testing.n.dto.answer.for_test;

public class TestMatchableAnswerDto {
    private TestAnswerOptionDto key;
    private TestAnswerOptionDto value;

    public TestMatchableAnswerDto() {
    }

    public TestMatchableAnswerDto(TestAnswerOptionDto key, TestAnswerOptionDto value) {
        this.key = key;
        this.value = value;
    }

    public TestAnswerOptionDto getKey() {
        return key;
    }

    public void setKey(TestAnswerOptionDto key) {
        this.key = key;
    }

    public TestAnswerOptionDto getValue() {
        return value;
    }

    public void setValue(TestAnswerOptionDto value) {
        this.value = value;
    }
}
