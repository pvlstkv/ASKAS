package com.example.javaserver.testing.model.dto;

public class ThemeIn {
    private String name;
    private String decryption;
    private Long subjectId;
    private Integer questionQuantityInTest;
    private Integer attemptNumberInTest;

    public ThemeIn() {
    }

    public ThemeIn(String name, String decryption, Long subjectId, Integer questionQuantityInTest, Integer attemptNumberInTest) {
        this.name = name;
        this.decryption = decryption;
        this.subjectId = subjectId;
        this.questionQuantityInTest = questionQuantityInTest;
        this.attemptNumberInTest = attemptNumberInTest;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDecryption() {
        return decryption;
    }

    public void setDecryption(String decryption) {
        this.decryption = decryption;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public Integer getQuestionQuantityInTest() {
        return questionQuantityInTest;
    }

    public void setQuestionQuantityInTest(Integer questionQuantityInTest) {
        this.questionQuantityInTest = questionQuantityInTest;
    }

    public Integer getAttemptNumberInTest() {
        return attemptNumberInTest;
    }

    public void setAttemptNumberInTest(Integer attemptNumberInTest) {
        this.attemptNumberInTest = attemptNumberInTest;
    }
}
