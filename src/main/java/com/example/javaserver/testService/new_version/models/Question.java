package com.example.javaserver.testService.new_version.models;


import com.example.javaserver.common_data.model.Subject;
import com.example.javaserver.common_data.model.Theme;
import com.example.javaserver.testService.new_version.configs.QuestionType;
import com.example.javaserver.testService.new_version.models.InOutComingModels.QuestionIn;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity

public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //    @Column(columnDefinition = "TEXT") todo @Lob or something not like varchar(255)
    private String question;
    // todo like the required annotation like the question field
    private QuestionType questionType;
    //    @JsonIgnore
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AnswerChoice> answerChoiceList = new ArrayList<>();
    private Double complexity;

    @ManyToOne()
    @JsonIgnore
    private Theme theme;

    @ManyToOne()
    @JsonIgnore
    private Subject subject;

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

    public void setId(Long id) {
        this.id = id;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
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

    public Long getId() {
        return id;
    }

    public Question() {
    }

    public Double getComplexity() {
        return complexity;
    }

    public void setComplexity(Double complexity) {
        this.complexity = complexity;
    }

    public List<String> fetchRightAnswers() {
        return this.answerChoiceList.stream().filter(AnswerChoice::getRight)
                .map(AnswerChoice::getAnswer).collect(Collectors.toList());
    }

    public List<String> fetchValuesOfAnswerChoice() {
        return this.answerChoiceList.stream().map(AnswerChoice::getAnswer).collect(Collectors.toList());
    }


    public Question(QuestionIn questionIn, Subject subject, Theme theme) {
        this.id = questionIn.getId();
        this.subject = subject;
        this.question = questionIn.getQuestion();
        this.complexity = questionIn.getComplexity();
        this.theme = theme;
        this.questionType = questionIn.getQuestionType();
        this.answerChoiceList = questionIn.getAnswers().stream().map(strAns ->
                new AnswerChoice(strAns, false)).collect(Collectors.toList());
        if (this.answerChoiceList.size() == 0) {
            this.answerChoiceList.add(new AnswerChoice(questionIn.getRightAnswers().get(0), true));
            return;
        }
        this.answerChoiceList.stream().filter(answerChoice -> questionIn.getRightAnswers().contains(answerChoice.getAnswer()))
                .forEach(answerChoice -> answerChoice.setRight(true));
    }


}
