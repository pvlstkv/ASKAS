package com.example.javaserver.testing.model.new_;

import com.example.javaserver.common_data.model.Subject;
import com.example.javaserver.study.model.UserFile;
import com.example.javaserver.testing.config.QuestionType;
import com.example.javaserver.testing.model.Theme;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Set;

@Entity
@DiscriminatorValue("choose_and_seq")
public class QuestionChooseAndSeqNew extends QuestionNew {
    @OneToMany(mappedBy = "questionChooseAndSeqNew", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AnswerOptionNew> answerOptionNewList;

    public QuestionChooseAndSeqNew() {

    }

    public List<AnswerOptionNew> getAnswerOptionNewList() {
        return answerOptionNewList;
    }

    public void setAnswerOptionNewList(List<AnswerOptionNew> answerOptionNewList) {
        this.answerOptionNewList = answerOptionNewList;
    }

    public QuestionChooseAndSeqNew(Long id, String question, QuestionType questionType, Double complexity, Set<UserFile> userFiles, Theme theme, Subject subject, List<AnswerOptionNew> answerOptionNewList) {
        super(id, question, questionType, complexity, userFiles, theme, subject);
        this.answerOptionNewList = answerOptionNewList;
    }

    public QuestionChooseAndSeqNew(List<AnswerOptionNew> answerOptionNewList) {
        this.answerOptionNewList = answerOptionNewList;
    }
}
