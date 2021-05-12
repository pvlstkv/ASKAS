package com.example.javaserver.testing.new_v.dto.answer.for_test;

public class TestAnswerOptionDto {
    private Long id;
    private String answer;
    private Long fileId;

    public TestAnswerOptionDto() {
    }

    public TestAnswerOptionDto(Long id, String answer, Long fileId) {
        this.id = id;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
