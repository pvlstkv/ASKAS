package com.example.javaserver.testService.new_version.models.saving_results;

import com.example.javaserver.testService.new_version.models.AnswerChoice;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user_answers")
public class UserAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String answer;
    private Boolean isRight;
    @ManyToOne()
    @JoinColumn(name = "passed_question_id")
    @JsonIgnore
    private PassedQuestion passedQuestion;

    public UserAnswer() {
    }

    public UserAnswer(String answer, Boolean isRight, PassedQuestion passedQuestion) {
        this.answer = answer;
        this.isRight = isRight;
        this.passedQuestion = passedQuestion;
    }

    public Boolean isRight() {
        return isRight;
    }

    public void setRight(Boolean right) {
        isRight = right;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public PassedQuestion getPassedQuestion() {
        return passedQuestion;
    }

    public void setPassedQuestion(PassedQuestion passedQuestion) {
        this.passedQuestion = passedQuestion;
    }
}
