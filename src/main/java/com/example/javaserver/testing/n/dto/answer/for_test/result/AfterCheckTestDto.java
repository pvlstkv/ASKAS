package com.example.javaserver.testing.n.dto.answer.for_test.result;

import jdk.dynalink.linker.LinkerServices;

import java.util.List;

public class AfterCheckTestDto {
    private List<AfterCheckQuestionDto> questions;
    private Long rating;

    public AfterCheckTestDto() {
    }

    public AfterCheckTestDto(List<AfterCheckQuestionDto> questions, Long rating) {
        this.questions = questions;
        this.rating = rating;
    }

    public List<AfterCheckQuestionDto> getQuestions() {
        return questions;
    }

    public void setQuestions(List<AfterCheckQuestionDto> questions) {
        this.questions = questions;
    }

    public Long getRating() {
        return rating;
    }

    public void setRating(Long rating) {
        this.rating = rating;
    }
}
