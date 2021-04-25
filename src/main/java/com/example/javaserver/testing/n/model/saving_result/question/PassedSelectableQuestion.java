package com.example.javaserver.testing.n.model.saving_result.question;

import com.example.javaserver.testing.n.model.QuestionData;
import com.example.javaserver.testing.n.model.saving_result.PassedTest;
import com.example.javaserver.testing.n.model.saving_result.answer.PassedSelectableAnswer;
import com.example.javaserver.testing.n.model.saving_result.question.PassedQuestionData;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@DiscriminatorValue("choose_and_seq")
public class PassedSelectableQuestion extends PassedQuestionData {

    @OneToMany(mappedBy = "passedSelectableQuestion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PassedSelectableAnswer> userAnswers;

    public PassedSelectableQuestion(List<PassedSelectableAnswer> userAnswers) {
        this.userAnswers = userAnswers;
    }

    public PassedSelectableQuestion(QuestionData questionData, PassedTest passedTest, List<PassedSelectableAnswer> userAnswers) {
        super(questionData, passedTest);
        this.userAnswers = userAnswers;
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
