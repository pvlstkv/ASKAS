package com.example.javaserver.testing.new_v.dto.answer.for_test.result;

import java.time.OffsetDateTime;
import java.util.List;

public class AfterCheckTestDto {
    private List<AfterCheckQuestionDto> questions;
    private Integer rating;
    private OffsetDateTime passedAt;

    public AfterCheckTestDto() {
    }

    public AfterCheckTestDto(List<AfterCheckQuestionDto> questions, Integer rating) {
        this.questions = questions;
        this.rating = rating;
    }

    public AfterCheckTestDto(List<AfterCheckQuestionDto> questions, Integer rating, OffsetDateTime passedAt) {
        this.questions = questions;
        this.rating = rating;
        this.passedAt = passedAt;
    }

    public List<AfterCheckQuestionDto> getQuestions() {
        return questions;
    }

    public void setQuestions(List<AfterCheckQuestionDto> questions) {
        this.questions = questions;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public OffsetDateTime getPassedAt() {
        return passedAt;
    }

    public void setPassedAt(OffsetDateTime passedAt) {
        this.passedAt = passedAt;
    }
}
