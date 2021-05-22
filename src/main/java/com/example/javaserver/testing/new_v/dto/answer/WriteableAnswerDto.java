package com.example.javaserver.testing.new_v.dto.answer;

public class WriteableAnswerDto {
   private String answer;
    private Boolean isStrict;

    public WriteableAnswerDto(String answer, Boolean isStrict) {
        this.answer = answer;
        this.isStrict = isStrict;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Boolean getStrict() {
        return isStrict;
    }

    public void setStrict(Boolean strict) {
        isStrict = strict;
    }
}
