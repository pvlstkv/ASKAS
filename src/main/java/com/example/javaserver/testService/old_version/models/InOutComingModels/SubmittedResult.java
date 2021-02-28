package com.example.javaserver.testService.old_version.models.InOutComingModels;

import java.util.List;

public class SubmittedResult {
    private String subject;
    private List<SubmittedResultQuestion> submittedResultQuestion;
    private Long rating;

    public SubmittedResult(String subject, List<SubmittedResultQuestion> submittedResultQuestion, Long rating) {
        this.subject = subject;
        this.submittedResultQuestion = submittedResultQuestion;
        this.rating = rating;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<SubmittedResultQuestion> getSubmittedResultQuestion() {
        return submittedResultQuestion;
    }

    public void setSubmittedResultQuestion(List<SubmittedResultQuestion> submittedResultQuestion) {
        this.submittedResultQuestion = submittedResultQuestion;
    }

    public Long getRating() {
        return rating;
    }

    public void setRating(Long rating) {
        this.rating = rating;
    }
}
