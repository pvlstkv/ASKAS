package com.example.javaserver.testService.models.InOutComingModels;


import java.util.List;

public class SubmittedTest {
    String subject;
    List<SubmittedQuestion> questions;

    public SubmittedTest(String subject, List<SubmittedQuestion> questions) {
        this.subject = subject;
        this.questions = questions;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<SubmittedQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(List<SubmittedQuestion> questions) {
        this.questions = questions;
    }
}
