package com.example.javaserver.testing.n.dto.question;

import com.example.javaserver.testing.config.QuestionType;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Null;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class QuestionDataDto {

    private Long id;
    private String question;
    private QuestionType questionType;
    private Double complexity;
    private Set<Long> fileIds;

    private List<Map<String, Object>> answers;

    public QuestionDataDto(Long id, String question, QuestionType questionType, Double complexity, Set<Long> fileIds, List<Map<String, Object>> answers) {
        this.id = id;
        this.question = question;
        this.questionType = questionType;
        this.complexity = complexity;
        this.fileIds = fileIds;
        this.answers = answers;
    }

    public QuestionDataDto(String question, QuestionType questionType, Double complexity, Set<Long> fileIds, List<Map<String, Object>> answers) {
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

    public List<Map<String, Object>> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Map<String, Object>> answers) {
        this.answers = answers;
    }
}
