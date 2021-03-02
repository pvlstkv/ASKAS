package com.example.javaserver.testService.old_version.models.saving_results;

import com.example.javaserver.testService.old_version.models.AnswerChoiceOld;
import com.example.javaserver.testService.old_version.models.QuestionOld;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
public class PassedQuestion implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne
    private QuestionOld questionOld;
    @OneToMany(mappedBy = "passed_question", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AnswerChoiceOld> userAnswers;

    public PassedQuestion(Integer id, QuestionOld questionOld, Set<AnswerChoiceOld> userAnswers) {
        this.id = id;
        this.questionOld = questionOld;
        this.userAnswers = userAnswers;
    }

    public PassedQuestion(QuestionOld questionOld, Set<AnswerChoiceOld> userAnswers) {
        this.questionOld = questionOld;
        this.userAnswers = userAnswers;
    }

    public PassedQuestion() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public QuestionOld getQuestion() {
        return questionOld;
    }

    public void setQuestion(QuestionOld questionOld) {
        this.questionOld = questionOld;
    }

    public Set<AnswerChoiceOld> getUserAnswers() {
        return userAnswers;
    }

    public void setUserAnswers(Set<AnswerChoiceOld> userAnswers) {
        this.userAnswers = userAnswers;
    }
}
