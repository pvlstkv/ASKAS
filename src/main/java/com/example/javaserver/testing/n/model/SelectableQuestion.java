package com.example.javaserver.testing.n.model;

import com.example.javaserver.common_data.model.Subject;
import com.example.javaserver.study.model.UserFile;
import com.example.javaserver.testing.config.QuestionType;
import com.example.javaserver.testing.model.Theme;
import com.example.javaserver.testing.n.model.answer.AnswerOption;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@DiscriminatorValue("choose_and_seq")
public class SelectableQuestion extends QuestionData {

    @OneToMany(mappedBy = "selectableQuestion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AnswerOption> answerOptionList;

    public SelectableQuestion(QuestionData questionData, List<AnswerOption> answerOptionList){
        super(questionData);
        this.answerOptionList = answerOptionList;
    }
    public SelectableQuestion( String question, QuestionType questionType, Double complexity, Set<UserFile> userFiles, Theme theme, Subject subject, List<AnswerOption> answerOptionList) {
        super( question, questionType, complexity, userFiles, theme, subject);
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
}
