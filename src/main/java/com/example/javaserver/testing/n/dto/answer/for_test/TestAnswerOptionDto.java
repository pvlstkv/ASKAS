package com.example.javaserver.testing.n.dto.answer.for_test;

public class TestAnswerOptionDto {
    private String answer;
    private Long fileId;

    public TestAnswerOptionDto() {
    }

    public TestAnswerOptionDto(String answer, Long fileId) {
        this.answer = answer;
        this.fileId = fileId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }
}
