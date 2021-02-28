package com.example.javaserver.testService.old_version.models.InOutComingModels;


import java.util.List;

public class RequestedTest {
    private String subject;
    private List<RequestedQuestion> requestedQuestions;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<RequestedQuestion> getRequestedQuestions() {
        return requestedQuestions;
    }

    public void setRequestedQuestions(List<RequestedQuestion> requestedQuestions) {
        this.requestedQuestions = requestedQuestions;
    }

    public RequestedTest() {
    }

    public RequestedTest(String subject, List<RequestedQuestion> requestedQuestions) {
        this.subject = subject;
        this.requestedQuestions = requestedQuestions;
    }
}
