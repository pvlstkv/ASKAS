package com.example.javaserver.testing.new_v.model.saving_result.question;

import com.example.javaserver.testing.new_v.model.saving_result.PassedTestN;
import com.example.javaserver.testing.new_v.model.saving_result.answer.Answerable;
import com.example.javaserver.testing.new_v.model.saving_result.answer.PassedWriteableAnswer;
import com.example.javaserver.testing.new_v.model.question.QuestionData;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Entity
@DiscriminatorValue("write")
public class PassedWriteableQuestion extends PassedQuestionData implements Serializable, Questionable {

    @OneToMany(mappedBy = "passedWriteableQuestion",
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PassedWriteableAnswer> userAnswers;

    public PassedWriteableQuestion() {

    }

    public PassedWriteableQuestion(QuestionData questionData, PassedTestN passedTestN) {
        super(questionData, passedTestN);

    }

    public PassedWriteableQuestion(List<PassedWriteableAnswer> userAnswers) {
        this.userAnswers = userAnswers;
    }

    public PassedWriteableQuestion(QuestionData questionData, PassedTestN passedTestN, List<PassedWriteableAnswer> userAnswers) {
        super(questionData, passedTestN);
        this.userAnswers = userAnswers;
    }

    public List<PassedWriteableAnswer> getUserAnswers() {
        return userAnswers;
    }

    public void setUserAnswers(List<PassedWriteableAnswer> userAnswers) {
        this.userAnswers = userAnswers;
    }

    @Override
    public List<Answerable> getAnswers() {
        return Collections.singletonList((Answerable) userAnswers);
    }
}
