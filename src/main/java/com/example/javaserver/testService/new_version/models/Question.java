package com.example.javaserver.testService.new_version.models;


import com.example.javaserver.common_data.model.Subject;
import com.example.javaserver.testService.new_version.configs.QuestionType;
import com.example.javaserver.testService.new_version.models.InOutComingModels.QuestionIn;
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
    private String theme; // todo like required annotation like the question field
    private QuestionType questionType;
    @JsonIgnore
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AnswerChoice> answerChoiceList = new ArrayList<>();
    private Double complexity;


    public List<AnswerChoice> getAnswerChoiceList() {
        return answerChoiceList;
    }

    public void setAnswerChoiceList(List<AnswerChoice> answerChoiceList) {
        this.answerChoiceList = answerChoiceList;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Question() {
    }

    public Double getComplexity() {
        return complexity;
    }

    public void setComplexity(Double complexity) {
        this.complexity = complexity;
    }

    public Question(QuestionIn questionIn, Subject subject) {
        this.id = questionIn.getId();
        this.subject = subject;
        this.question = questionIn.getQuestion();
        this.complexity = questionIn.getComplexity();
        this.answerChoiceList = questionIn.getAnswers().stream().map(strAns ->
                new AnswerChoice(strAns, false)).collect(Collectors.toList());
        if (this.answerChoiceList.size() == 0) {
            this.answerChoiceList.add(new AnswerChoice(questionIn.getRightAnswers().get(0), true));
            return;
        }
        this.answerChoiceList.stream().filter(answerChoice -> questionIn.getRightAnswers().contains(answerChoice.getAnswer()))
                .forEach(answerChoice -> answerChoice.setRight(true));
    }

    public Question(Integer id, String question, Double complexity) {
        this.id = id;
        this.question = question;
        this.complexity = complexity;
    }

}
