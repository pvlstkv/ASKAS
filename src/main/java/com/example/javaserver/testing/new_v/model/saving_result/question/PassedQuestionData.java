package com.example.javaserver.testing.new_v.model.saving_result.question;

import com.example.javaserver.testing.new_v.model.saving_result.PassedTestN;
import com.example.javaserver.testing.new_v.model.question.QuestionData;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "passed_question_discriminator",
        discriminatorType = DiscriminatorType.STRING)
public class PassedQuestionData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private QuestionData questionData;
    @JsonIgnore
    @ManyToOne
    private PassedTestN passedTestN;

    public PassedQuestionData() {
    }

    public PassedQuestionData(QuestionData questionData, PassedTestN passedTestN) {
        this.questionData = questionData;
        this.passedTestN = passedTestN;
    }

    public PassedQuestionData(PassedTestN passedTestN) {
        this.passedTestN = passedTestN;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public QuestionData getQuestionData() {
        return questionData;
    }

    public void setQuestionData(QuestionData questionData) {
        this.questionData = questionData;
    }

    public PassedTestN getPassedTest() {
        return passedTestN;
    }

    public void setPassedTest(PassedTestN passedTestN) {
        this.passedTestN = passedTestN;
    }
}
