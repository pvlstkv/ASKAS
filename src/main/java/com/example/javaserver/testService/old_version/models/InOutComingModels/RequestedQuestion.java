package com.example.javaserver.testService.old_version.models.InOutComingModels;


import com.example.javaserver.testService.old_version.models.Question;

import java.util.List;

public class RequestedQuestion extends SubmittedQuestion {
    private  List<String> rightAnswers;
    private Double complexity;

    public RequestedQuestion() {
    }

    public RequestedQuestion(List<String> rightAnswers, Double complexity) {
        this.rightAnswers = rightAnswers;
        this.complexity = complexity;
    }

    public RequestedQuestion(Question question, List<String> rightAnswers, Double complexity) {
        super(question);
        this.rightAnswers = rightAnswers;
        this.complexity = complexity;
    }

    public RequestedQuestion(Long id, String question, List<String> answers, List<String> rightAnswers, Double complexity) {
        super(id, question, answers);
        this.rightAnswers = rightAnswers;
        this.complexity = complexity;
    }

    public RequestedQuestion(String question, List<String> answers, List<String> rightAnswers, Double complexity) {
        super(question, answers);
        this.rightAnswers = rightAnswers;
        this.complexity = complexity;
    }

    public List<String> getRightAnswers() {
        return rightAnswers;
    }

    public void setRightAnswers(List<String> rightAnswers) {
        this.rightAnswers = rightAnswers;
    }

    public Double getComplexity() {
        return complexity;
    }

    public void setComplexity(Double complexity) {
        this.complexity = complexity;
    }
    //    private Long id;
////    private String subjectName;
//    private String title;
//    private String questionText;
//    private List<String> answersChoice;
//    private List<String> rightAnswers;
//    private Double complexity;
//
//

//
//    // конструктор для создания ворпоса
//    public RequestedQuestion(String subjectName, String title,
//                             String questionText, List<String> answersChoice,
//                             List<String> rightAnswers
//            , Double complexity
//    ) {
////        this.subjectName = subjectName;
//        this.title = title;
//        this.questionText = questionText;
//        this.answersChoice = answersChoice;
//        this.rightAnswers = rightAnswers;
//                this.complexity = complexity;
//    }
//
//    // конструктор для обновления вопроса
//    public RequestedQuestion(Long id, String subjectName, String title,
//                             String questionText, List<String> answersChoice,
//                             List<String> rightAnswers
//                                         , Double complexity
//    ) {
//        this.id = id;
////        this.subjectName = subjectName;
//        this.title = title;
//        this.questionText = questionText;
//        this.answersChoice = answersChoice;
//        this.rightAnswers = rightAnswers;
//        this.complexity = complexity;
//    }
//
//    public List<String> getAnswersChoice() {
//        return answersChoice;
//    }
//
//    public void setAnswersChoice(List<String> answersChoice) {
//        this.answersChoice = answersChoice;
//    }
//
//    public List<String> getRightAnswers() {
//        return rightAnswers;
//    }
//
//    public void setRightAnswers(List<String> rightAnswers) {
//        this.rightAnswers = rightAnswers;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
////    public String getSubjectName() {
////        return subjectName;
////    }
////
////    public void setSubjectName(String subjectName) {
////        this.subjectName = subjectName;
////    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getQuestionText() {
//        return questionText;
//    }
//
//    public void setQuestionText(String questionText) {
//        this.questionText = questionText;
//    }
//
//
//    public Double getComplexity() {
//        return complexity;
//    }
//
//    public boolean setComplexity(Double complexity) {
//        if (ComplexityRange.INSTANCE.getLowLevel() <= complexity &&
//                complexity <= ComplexityRange.INSTANCE.getHighLevel()
//        ) {
//            this.complexity = complexity;
//            return true;
//        }
//        return false;
//    }
}
