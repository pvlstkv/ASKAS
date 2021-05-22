package com.example.javaserver.testing.new_v.dto.question;

import com.example.javaserver.testing.new_v.config.QuestionType;

import java.util.Set;

public class QuestionDataDto {

    private Long id;
    private String question;
    private QuestionType questionType;
    private Double complexity;
    private Set<Long> fileIds;
//    List<Map<String, Object>>
    private Object answers;

    public QuestionDataDto(Long id, String question, QuestionType questionType, Double complexity, Set<Long> fileIds, Object answers) {
        this.id = id;
        this.question = question;
        this.questionType = questionType;
        this.complexity = complexity;
        this.fileIds = fileIds;
        this.answers = answers;
    }

    public QuestionDataDto(String question, QuestionType questionType, Double complexity, Set<Long> fileIds, Object answers) {
        this.question = question;
        this.questionType = questionType;
        this.complexity = complexity;
        this.fileIds = fileIds;
        this.answers = answers;
    }

    public QuestionDataDto() {
    }

    public QuestionDataDto(String question, QuestionType questionType, Double complexity, Set<Long> fileIds) {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Object getAnswers() {
        return answers;
    }

    public void setAnswers(Object answers) {
        this.answers = answers;
    }
}
