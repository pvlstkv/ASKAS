//package com.example.javaserver.testing.model;
//
//
//import com.example.javaserver.common_data.model.Subject;
//import com.example.javaserver.study.model.UserFile;
//import com.example.javaserver.testing.config.QuestionType;
//import com.example.javaserver.testing.model.dto.QuestionIn;
//import com.fasterxml.jackson.annotation.*;
//
//import javax.persistence.*;
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@Entity
////@Table(name = "questions")
//public class QuestionMatch implements Serializable {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    //    @Column(columnDefinition = "TEXT") todo @Lob or something not like varchar(255)
//    private String question;
//    // todo like the required annotation like the question field
//    private QuestionType questionType;
//    //    @JsonIgnore
////    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
//    @OneToMany
//    private List<Match> matchingList;
//    private Double complexity;
//    // todo how to do
//
//    @JsonProperty("fileIds")
//    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
//    @JsonIdentityReference(alwaysAsId = true)
//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<UserFile> userFiles;
//
//    @ManyToOne
//    @JsonIgnore
//    private Theme theme;
//
//    @ManyToOne()
//    @JsonIgnore
//    private Subject subject;
//
////    public QuestionMatch(QuestionIn questionIn, Subject subject, Theme theme) {
////        this.id = questionIn.getId();
////        this.subject = subject;
////        this.question = questionIn.getQuestion();
////        this.complexity = questionIn.getComplexity();
////        this.theme = theme;
////        this.questionType = questionIn.getQuestionType();
////        this.answerChoiceList = questionIn.getAnswers().stream().map(strAns ->
////                new AnswerChoice(strAns, false)).collect(Collectors.toList());
////        if (this.answerChoiceList.size() == 0) {
////            this.answerChoiceList.add(new AnswerChoice(questionIn.getRightAnswers().get(0), true));
////            return;
////        }
////        this.answerChoiceList.stream().filter(answerChoice -> questionIn.getRightAnswers().contains(answerChoice.getAnswer()))
////                .forEach(answerChoice -> answerChoice.setRight(true));
////    }
//
//    public QuestionMatch() {
//    }
//
//    public QuestionMatch(Long id) {
//        this.id = id;
//    }
//
//    public List<Match> getMatchingList() {
//        return matchingList;
//    }
//
//    public void setMatchingList(List<Match> matchingList) {
//        this.matchingList = matchingList;
//    }
//
//    public QuestionMatch(Long id, String question, QuestionType questionType, List<Match> matchingList, Double complexity) {
//        this.id = id;
//        this.question = question;
//        this.questionType = questionType;
//        this.matchingList = matchingList;
//        this.complexity = complexity;
//    }
//
//    public Set<UserFile> getUserFiles() {
//        return userFiles;
//    }
//
//    public void setUserFiles(Set<UserFile> userFiles) {
//        this.userFiles = userFiles;
//    }
//
////    public List<AnswerChoice> getAnswerChoiceList() {
////        return answerChoiceList;
////    }
////
////    public void setAnswerChoiceList(List<AnswerChoice> answerChoiceList) {
////        this.answerChoiceList = answerChoiceList;
////    }
//
//    public Subject getSubject() {
//        return subject;
//    }
//
//    public void setSubject(Subject subject) {
//        this.subject = subject;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public Theme getTheme() {
//        return theme;
//    }
//
//    public void setTheme(Theme theme) {
//        this.theme = theme;
//    }
//
//    public String getQuestion() {
//        return question;
//    }
//
//    public void setQuestion(String question) {
//        this.question = question;
//    }
//
//    public QuestionType getQuestionType() {
//        return questionType;
//    }
//
//    public void setQuestionType(QuestionType questionType) {
//        this.questionType = questionType;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//
//    public Double getComplexity() {
//        return complexity;
//    }
//
//    public void setComplexity(Double complexity) {
//        this.complexity = complexity;
//    }
//
////    public List<String> fetchRightAnswers() {
////        return this.answerChoiceList.stream().filter(AnswerChoice::getRight)
////                .map(AnswerChoice::getAnswer).collect(Collectors.toList());
////    }
////
////    public List<String> fetchValuesOfAnswerChoice() {
////        return this.answerChoiceList.stream().map(AnswerChoice::getAnswer).collect(Collectors.toList());
////    }
//
//
//    public List<Long> fetchUserFilesIds() {
//        List<Long> ids = new ArrayList<>();
//        for (UserFile file : this.userFiles) {
//            ids.add(file.getId());
//        }
//        return ids;
//    }
//
//}
