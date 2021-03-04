package com.example.javaserver.testService.new_version.models.InOutComingModels.passed_test;

import com.example.javaserver.testService.new_version.models.saving_results.PassedTest;
import com.example.javaserver.user.model.User;

import java.time.OffsetDateTime;
import java.util.List;

public class TestOut {
//    private String Subject;
//    private String theme;
//    private Long SubjectId;
//    private Long themeId;
    private List<PassedTest> passedTests;
//    private OffsetDateTime passedAt;
//    private Double rating;

    public TestOut() {
    }
//
//    public TestOut(String subject, String theme, Long subjectId, Long themeId, List<PassedTest> passedTests, OffsetDateTime passedAt, Double rating) {
//        Subject = subject;
//        this.theme = theme;
//        SubjectId = subjectId;
//        this.themeId = themeId;
//        this.passedTests = passedTests;
//        this.passedAt = passedAt;
//        this.rating = rating;
//    }
//
//    public String getSubject() {
//        return Subject;
//    }
//
//    public void setSubject(String subject) {
//        Subject = subject;
//    }
//
//    public String getTheme() {
//        return theme;
//    }
//
//    public void setTheme(String theme) {
//        this.theme = theme;
//    }
//
//    public Long getSubjectId() {
//        return SubjectId;
//    }
//
//    public void setSubjectId(Long subjectId) {
//        SubjectId = subjectId;
//    }
//
//    public Long getThemeId() {
//        return themeId;
//    }
//
//    public void setThemeId(Long themeId) {
//        this.themeId = themeId;
//    }

    public List<PassedTest> getPassedTests() {
        return passedTests;
    }

    public void setPassedTests(List<PassedTest> passedTests) {
        this.passedTests = passedTests;
    }

//    public OffsetDateTime getPassedAt() {
//        return passedAt;
//    }
//
//    public void setPassedAt(OffsetDateTime passedAt) {
//        this.passedAt = passedAt;
//    }
//
//    public Double getRating() {
//        return rating;
//    }
//
//    public void setRating(Double rating) {
//        this.rating = rating;
//    }
}
