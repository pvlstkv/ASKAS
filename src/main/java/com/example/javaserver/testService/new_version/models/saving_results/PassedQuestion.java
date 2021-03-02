package com.example.javaserver.testService.new_version.models.saving_results;


import com.example.javaserver.testService.new_version.models.Question;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "passed_questions")
public class PassedQuestion implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Question question;
    @ManyToOne(fetch = FetchType.LAZY)
    private PassedTest passedTest;
    @OneToMany(mappedBy = "passedQuestion",cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserAnswer> userAnswers;

    public PassedQuestion(Long id, Question question, PassedTest passedTest, Set<UserAnswer> userAnswers) {
        this.id = id;
        this.question = question;
        this.passedTest = passedTest;
        this.userAnswers = userAnswers;
    }

    public PassedQuestion(Question question, PassedTest passedTest, Set<UserAnswer> userAnswers) {
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

    public Set<UserAnswer> getUserAnswers() {
        return userAnswers;
    }

    public void setUserAnswers(Set<UserAnswer> userAnswers) {
        this.userAnswers = userAnswers;
    }
}
