package com.example.javaserver.testing.new_v.model.question;

import com.example.javaserver.common_data.model.Subject;
import com.example.javaserver.study.model.UserFile;
import com.example.javaserver.testing.new_v.config.QuestionType;
import com.example.javaserver.testing.theme.Theme;
import com.example.javaserver.testing.new_v.model.answer.AnswerOption;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Set;

@Entity
@DiscriminatorValue("choose_and_seq")
public class SelectableQuestion extends QuestionData {

    @OneToMany(mappedBy = "selectableQuestion", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonProperty("answerList")
    private List<AnswerOption> answerOptionList;

    public SelectableQuestion(QuestionData questionData) {
        super(questionData);
    }

    public SelectableQuestion(QuestionData questionData, List<AnswerOption> answerOptionList) {
        super(questionData);
        this.answerOptionList = answerOptionList;
    }

    public SelectableQuestion(String question, QuestionType questionType, Double complexity, Set<UserFile> userFiles, Theme theme, Subject subject, List<AnswerOption> answerOptionList) {
        super(question, questionType, complexity, userFiles, theme, subject);
        this.answerOptionList = answerOptionList;
    }

    public SelectableQuestion() {

    }

    public SelectableQuestion(Long id, String question, QuestionType questionType, Double complexity, Set<UserFile> userFiles, Theme theme, Subject subject, List<AnswerOption> answerOptionList) {
        super(id, question, questionType, complexity, userFiles, theme, subject);
        this.answerOptionList = answerOptionList;
    }

    public List<AnswerOption> getAnswerOptionList() {
        return answerOptionList;
    }

    public void setAnswerOptionList(List<AnswerOption> answerOptionList) {
        this.answerOptionList = answerOptionList;
    }

    public SelectableQuestion(List<AnswerOption> answerOptionList) {
        this.answerOptionList = answerOptionList;
    }

    public void putNewAnswers(List<AnswerOption> answerOptionList) {
        this.answerOptionList.clear();
        answerOptionList.forEach(it -> {
            it.setSelectableQuestion(this);
            this.answerOptionList.add(it);
        });
//        this.answerOptionList.add(this.answerOptionList.get(0));
    }
}
