package com.example.javaserver.testService.old_version.models;


import com.example.javaserver.common_data.model.Subject;
import com.example.javaserver.testService.old_version.models.InOutComingModels.RequestedQuestion;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity

public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Subject subject;

//    @Column(columnDefinition = "TEXT") todo @Lob or something not like varchar(255)
    private String question;

    @JsonIgnore
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AnswerChoice> answerChoiceList = new ArrayList<>();

    private Double complexity;

    private String type;

    public String getType() {
        return type;
    }

    public List<AnswerChoice> getAnswerChoiceList() {
        return answerChoiceList;
    }

    public void setAnswerChoiceList(List<AnswerChoice> answerChoiceList) {
        this.answerChoiceList = answerChoiceList;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Question() {    }

    public Double getComplexity() {
        return complexity;
    }

    public void setComplexity(Double complexity) {
        this.complexity = complexity;
    }

    public Question(RequestedQuestion requestedQuestion, Subject subject) {
        this.id = requestedQuestion.getId();
        this.subject = subject;
        this.question = requestedQuestion.getQuestion();
        this.complexity = requestedQuestion.getComplexity();
        this.answerChoiceList = requestedQuestion.getAnswers().stream().map(strAns ->
                new AnswerChoice(strAns, false)).collect(Collectors.toList());
        if (this.answerChoiceList.size() == 0) {
            this.answerChoiceList.add(new AnswerChoice(requestedQuestion.getRightAnswers().get(0), true));
            return;
        }
        this.answerChoiceList.stream().filter(answerChoice -> requestedQuestion.getRightAnswers().contains(answerChoice.getAnswer()))
                .forEach(answerChoice -> answerChoice.setRight(true));
    }

    public Question(Integer id, String question, Double complexity) {
        this.id = id;
        this.question = question;
        this.complexity = complexity;
    }

}
