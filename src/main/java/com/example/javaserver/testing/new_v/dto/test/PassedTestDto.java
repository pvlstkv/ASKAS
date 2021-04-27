package com.example.javaserver.testing.new_v.dto.test;

import java.util.List;

public class PassedTestDto {

    private Integer userId;
    private String firstName;
    private String lastName;
    private String patronymic;

    private String themeName;
    private Long themeId;
    private List<Integer> results;

    public PassedTestDto(Integer userId, String firstName, String lastName, String patronymic, Long themeId, String themeName, List<Integer> results) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.themeName = themeName;
        this.themeId = themeId;
        this.results = results;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    public Long getThemeId() {
        return themeId;
    }

    public void setThemeId(Long themeId) {
        this.themeId = themeId;
    }

    public List<Integer> getResults() {
        return results;
    }

    public void setResults(List<Integer> results) {
        this.results = results;
    }
}
