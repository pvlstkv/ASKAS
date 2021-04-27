package com.example.javaserver.testing.new_v.dto.answer;

public class MatchableAnswerDto {
    private AnswerOptionDto key;
    private AnswerOptionDto value;

    public MatchableAnswerDto(AnswerOptionDto key, AnswerOptionDto value) {
        this.key = key;
        this.value = value;
    }

    public AnswerOptionDto getKey() {
        return key;
    }

    public void setKey(AnswerOptionDto key) {
        this.key = key;
    }

    public AnswerOptionDto getValue() {
        return value;
    }

    public void setValue(AnswerOptionDto value) {
        this.value = value;
    }
}
