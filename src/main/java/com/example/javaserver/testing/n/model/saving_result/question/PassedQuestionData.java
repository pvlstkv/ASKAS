package com.example.javaserver.testing.n.model.saving_result.question;

import com.example.javaserver.testing.n.model.QuestionData;
import com.example.javaserver.testing.n.model.saving_result.PassedTest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.parameters.P;

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
    private PassedTest passedTest;

    public PassedQuestionData() {
    }

    public PassedQuestionData(QuestionData questionData, PassedTest passedTest) {
        this.questionData = questionData;
        this.passedTest = passedTest;
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

    public PassedTest getPassedTest() {
        return passedTest;
    }

    public void setPassedTest(PassedTest passedTest) {
        this.passedTest = passedTest;
    }
}
