package com.example.javaserver.testing.new_v.dto.test;

import com.example.javaserver.testing.new_v.dto.question.QuestionDataDto;

import java.util.List;

public class TestDto {
    private Long subjectId;
    private Long themeId;
    private List<QuestionDataDto> questionDataDtoList;

    public TestDto() {
    }

    public TestDto(Long subjectId, Long themeId, List<QuestionDataDto> questionDataDtoList) {
        this.subjectId = subjectId;
        this.themeId = themeId;
        this.questionDataDtoList = questionDataDtoList;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public Long getThemeId() {
        return themeId;
    }

    public void setThemeId(Long themeId) {
        this.themeId = themeId;
    }

    public List<QuestionDataDto> getQuestionDataDtoList() {
        return questionDataDtoList;
    }

    public void setQuestionDataDtoList(List<QuestionDataDto> questionDataDtoList) {
        this.questionDataDtoList = questionDataDtoList;
    }
}
