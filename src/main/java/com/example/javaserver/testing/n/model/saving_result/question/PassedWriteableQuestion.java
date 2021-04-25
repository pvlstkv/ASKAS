package com.example.javaserver.testing.n.model.saving_result.question;

import com.example.javaserver.testing.n.model.QuestionData;
import com.example.javaserver.testing.n.model.saving_result.PassedTest;
import com.example.javaserver.testing.n.model.saving_result.answer.PassedWriteableAnswer;
import com.example.javaserver.testing.n.model.saving_result.question.PassedQuestionData;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@DiscriminatorValue("write")
public class PassedWriteableQuestion extends PassedQuestionData {

    @OneToMany(mappedBy = "passedWriteableQuestion",
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PassedWriteableAnswer> userAnswers;

    public PassedWriteableQuestion() {

    }
    public PassedWriteableQuestion(QuestionData questionData, PassedTest passedTest) {
        super(questionData, passedTest);

    }

    public PassedWriteableQuestion(List<PassedWriteableAnswer> userAnswers) {
        this.userAnswers = userAnswers;
    }

    public PassedWriteableQuestion(QuestionData questionData, PassedTest passedTest, List<PassedWriteableAnswer> userAnswers) {
        super(questionData, passedTest);
        this.userAnswers = userAnswers;
    }

    public List<PassedWriteableAnswer> getUserAnswers() {
        return userAnswers;
    }

    public void setUserAnswers(List<PassedWriteableAnswer> userAnswers) {
        this.userAnswers = userAnswers;
    }
}
