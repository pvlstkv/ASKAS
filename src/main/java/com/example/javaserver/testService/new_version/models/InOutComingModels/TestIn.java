package com.example.javaserver.testService.new_version.models.InOutComingModels;


import java.util.List;

public class TestIn {
    private Long subjectId;
    private List<QuestionIn> questionIns;
    private Long themeId;

    public TestIn() {
    }

    public TestIn(Long subjectId, List<QuestionIn> questionIns, Long themeId) {
        this.subjectId = subjectId;
        this.questionIns = questionIns;
        this.themeId = themeId;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public List<QuestionIn> getQuestionIns() {
        return questionIns;
    }

    public void setQuestionIns(List<QuestionIn> questionIns) {
        this.questionIns = questionIns;
    }

    public Long getThemeId() {
        return themeId;
    }

    public void setThemeId(Long themeId) {
        this.themeId = themeId;
    }
}
