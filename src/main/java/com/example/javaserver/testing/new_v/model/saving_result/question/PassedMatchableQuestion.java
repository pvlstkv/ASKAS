package com.example.javaserver.testing.new_v.model.saving_result.question;

import com.example.javaserver.testing.new_v.model.saving_result.PassedTestN;
import com.example.javaserver.testing.new_v.model.saving_result.answer.PassedMatchableAnswer;
import com.example.javaserver.testing.new_v.model.question.QuestionData;

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


    public PassedMatchableQuestion(QuestionData questionData, PassedTestN passedTestN) {
        super(questionData, passedTestN);

    }

    public PassedMatchableQuestion(List<PassedMatchableAnswer> userAnswers) {
        this.userAnswers = userAnswers;
    }

    public PassedMatchableQuestion(QuestionData questionData, PassedTestN passedTestN, List<PassedMatchableAnswer> userAnswers) {
        super(questionData, passedTestN);
        this.userAnswers = userAnswers;
    }

    public PassedMatchableQuestion(PassedTestN passedTest, List<PassedMatchableAnswer> userAnswers) {
        super(passedTest);
        this.userAnswers = userAnswers;
    }

    public List<PassedMatchableAnswer> getUserAnswers() {
        return userAnswers;
    }

    public void setUserAnswers(List<PassedMatchableAnswer> userAnswers) {
        this.userAnswers = userAnswers;
    }
}
