package com.example.javaserver.testing.n.dto.question;

import com.example.javaserver.testing.config.QuestionType;

import java.util.Set;

public class QuestionDataDto {
    private String question;
    private QuestionType questionType;
    private Double complexity;
    private Set<Long> fileIds;

    public QuestionDataDto(String question, QuestionType questionType, Double complexity, Set<Long> fileIds) {
        this.question = question;
        this.questionType = questionType;
        this.complexity = complexity;
        this.fileIds = fileIds;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public Double getComplexity() {
        return complexity;
    }

    public void setComplexity(Double complexity) {
        this.complexity = complexity;
    }

    public Set<Long> getFileIds() {
        return fileIds;
    }

    public void setFileIds(Set<Long> fileIds) {
        this.fileIds = fileIds;
    }
}
