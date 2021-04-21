//package com.example.javaserver.testing.model.dto;
//
//
//import com.example.javaserver.testing.config.QuestionType;
//import com.example.javaserver.testing.model.Question;
//
//import java.util.List;
//
//public class QuestionIn extends QuestionOut {
//    private List<String> rightAnswers;
//    private Double complexity;
//
//    public QuestionIn() {
//    }
//
//    public QuestionIn(List<String> rightAnswers, Double complexity) {
//        this.rightAnswers = rightAnswers;
//        this.complexity = complexity;
//    }
//
//    public QuestionIn(Question question, List<String> rightAnswers, Double complexity) {
//        super(question);
//        this.rightAnswers = rightAnswers;
//        this.complexity = complexity;
//    }
//
//    public QuestionIn(Long id, String question, List<String> answers, QuestionType questionType,
//                      List<String> rightAnswers, Double complexity, List<Long> fileIds) {
//        super(id, question, answers, questionType, fileIds);
//        this.rightAnswers = rightAnswers;
//        this.complexity = complexity;
//    }
//
//    public QuestionIn(String question, List<String> answers, QuestionType questionType,
//                      List<String> rightAnswers, Double complexity, List<Long> fileIds) {
//        super(question, answers, questionType, fileIds);
//        this.rightAnswers = rightAnswers;
//        this.complexity = complexity;
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
//    public Double getComplexity() {
//        return complexity;
//    }
//
//    public void setComplexity(Double complexity) {
//        this.complexity = complexity;
//    }
//
//}
