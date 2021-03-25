package com.example.javaserver.testing.model.saving_result;


import com.example.javaserver.testing.model.Question;
import com.fasterxml.jackson.annotation.JsonIgnore;


import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "passed_questions")
public class PassedQuestion implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    @OneToOne
    private Question question;
    @OneToMany(mappedBy = "passedQuestion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserAnswer> userAnswers;


    @ManyToOne()
    @JsonIgnore
    private PassedTest passedTest;

    public PassedQuestion(Long id, Question question, PassedTest passedTest, List<UserAnswer> userAnswers) {
        this.id = id;
        this.question = question;
        this.passedTest = passedTest;
        this.userAnswers = userAnswers;
    }

    public PassedQuestion(Question question, PassedTest passedTest, List<UserAnswer> userAnswers) {
        this.question = question;
        this.passedTest = passedTest;
        this.userAnswers = userAnswers;
    }

    public PassedTest getPassedTest() {
        return passedTest;
    }

    public void setPassedTest(PassedTest passedTest) {
        this.passedTest = passedTest;
    }

    public PassedQuestion() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public List<UserAnswer> getUserAnswers() {
        return userAnswers;
    }

    public void setUserAnswers(List<UserAnswer> userAnswers) {
        this.userAnswers = userAnswers;
    }
}
