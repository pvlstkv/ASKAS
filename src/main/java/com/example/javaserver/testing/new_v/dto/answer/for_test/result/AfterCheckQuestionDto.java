package com.example.javaserver.testing.new_v.dto.answer.for_test.result;

import com.example.javaserver.testing.new_v.model.question.QuestionData;

public class AfterCheckQuestionDto {
    private QuestionData questionData;
    private Object userAnswers;

    public AfterCheckQuestionDto() {
    }

    public AfterCheckQuestionDto(QuestionData questionData, Object userAnswer) {
        this.questionData = questionData;
        this.userAnswers = userAnswer;
    }

    public QuestionData getQuestionData() {
        return questionData;
    }

    public void setQuestionData(QuestionData questionData) {
        this.questionData = questionData;
    }

    public Object getUserAnswers() {
        return userAnswers;
    }

    public void setUserAnswers(Object userAnswers) {
        this.userAnswers = userAnswers;
    }
}
