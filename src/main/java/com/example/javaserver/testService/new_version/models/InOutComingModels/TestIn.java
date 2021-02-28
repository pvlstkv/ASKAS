package com.example.javaserver.testService.new_version.models.InOutComingModels;


import java.util.List;

public class TestIn {
    private Long subjectId;
    private List<QuestionIn> requestedQuestions;
    private String theme;

    public TestIn(Long subjectId, List<QuestionIn> requestedQuestions, String theme) {
        this.subjectId = subjectId;
        this.requestedQuestions = requestedQuestions;
        this.theme = theme;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public List<QuestionIn> getRequestedQuestions() {
        return requestedQuestions;
    }

    public void setRequestedQuestions(List<QuestionIn> requestedQuestions) {
        this.requestedQuestions = requestedQuestions;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }
}
