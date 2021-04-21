//package com.example.javaserver.testing.model.saving_result;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//
//import javax.persistence.*;
//import java.io.Serializable;
//
//@Entity
//@Table(name = "user_answers")
//public class UserAnswer implements Serializable {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String answer;
//    private Boolean isRight;
//    @ManyToOne()
//    @JoinColumn(name = "passed_question_id")
//    @JsonIgnore
//    private PassedQuestion passedQuestion;
//
//    public UserAnswer() {
//    }
//
//    public UserAnswer(String answer, Boolean isRight, PassedQuestion passedQuestion) {
//        this.answer = answer;
//        this.isRight = isRight;
//        this.passedQuestion = passedQuestion;
//    }
//
//    public Boolean isRight() {
//        return isRight;
//    }
//
//    public void setRight(Boolean right) {
//        isRight = right;
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
//    public String getAnswer() {
//        return answer;
//    }
//
//    public void setAnswer(String answer) {
//        this.answer = answer;
//    }
//
//    public PassedQuestion getPassedQuestion() {
//        return passedQuestion;
//    }
//
//    public void setPassedQuestion(PassedQuestion passedQuestion) {
//        this.passedQuestion = passedQuestion;
//    }
//}
