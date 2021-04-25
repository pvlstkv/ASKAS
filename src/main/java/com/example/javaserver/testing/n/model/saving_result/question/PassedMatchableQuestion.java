package com.example.javaserver.testing.n.model.saving_result.question;

import com.example.javaserver.testing.n.model.QuestionData;
import com.example.javaserver.testing.n.model.saving_result.PassedTest;
import com.example.javaserver.testing.n.model.saving_result.answer.PassedMatchableAnswer;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@DiscriminatorValue("match")
public class PassedMatchableQuestion extends PassedQuestionData {

    @OneToMany(mappedBy = "passedMatchableQuestion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PassedMatchableAnswer> userAnswers;

    public PassedMatchableQuestion() {

    }

    public PassedMatchableQuestion(List<PassedMatchableAnswer> userAnswers) {
        this.userAnswers = userAnswers;
    }

    public PassedMatchableQuestion(QuestionData questionData, PassedTest passedTest, List<PassedMatchableAnswer> userAnswers) {
        super(questionData, passedTest);
        this.userAnswers = userAnswers;
    }

    public List<PassedMatchableAnswer> getUserAnswers() {
        return userAnswers;
    }

    public void setUserAnswers(List<PassedMatchableAnswer> userAnswers) {
        this.userAnswers = userAnswers;
    }
}
