package com.example.javaserver.testing.n.dto.answer;

import com.example.javaserver.testing.n.dto.answer.for_test.TestAnswerOptionDto;

public class AnswerOptionDto extends TestAnswerOptionDto {
    private String answer;
    private Boolean isRight;
    private Long fileId;

    public AnswerOptionDto(String answer, Boolean isRight, Long fileId) {
        this.answer = answer;
        this.isRight = isRight;
        this.fileId = fileId;
    }

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

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }
}
