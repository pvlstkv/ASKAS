package com.example.javaserver.testing.new_v.model.saving_result.question;

import com.example.javaserver.testing.new_v.model.saving_result.PassedTestN;
import com.example.javaserver.testing.new_v.model.saving_result.answer.PassedSelectableAnswer;
import com.example.javaserver.testing.new_v.model.question.QuestionData;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Entity
@DiscriminatorValue("choose_and_seq")
public class PassedSelectableQuestion extends PassedQuestionData implements Serializable {

    @OneToMany(mappedBy = "passedSelectableQuestion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PassedSelectableAnswer> userAnswers;

    public PassedSelectableQuestion(List<PassedSelectableAnswer> userAnswers) {
        this.userAnswers = userAnswers;
    }

    public PassedSelectableQuestion(QuestionData questionData, PassedTestN passedTestN, List<PassedSelectableAnswer> userAnswers) {
        super(questionData, passedTestN);
        this.userAnswers = userAnswers;
    }

    public PassedSelectableQuestion(QuestionData questionData, PassedTestN passedTestN) {
        super(questionData, passedTestN);

    }

    public PassedSelectableQuestion() {

    }

    public List<PassedSelectableAnswer> getUserAnswers() {
        return userAnswers;
    }

    public void setUserAnswers(List<PassedSelectableAnswer> userAnswers) {
        this.userAnswers = userAnswers;
    }

}
