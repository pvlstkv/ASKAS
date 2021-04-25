package com.example.javaserver.testing.n.model;

import com.example.javaserver.common_data.model.Subject;
import com.example.javaserver.study.model.UserFile;
import com.example.javaserver.testing.n.config.QuestionType;
import com.example.javaserver.testing.model.Theme;
import com.example.javaserver.testing.n.model.answer.MatchableAnswerOption;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@DiscriminatorValue("match")
public class MatchableQuestion extends QuestionData implements Serializable {

    @OneToMany(mappedBy = "matchableQuestion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MatchableAnswerOption> matchableAnswerOptionList;

    public MatchableQuestion() {
    }

    public MatchableQuestion(QuestionData questionData) {
        super(questionData);
    }


    public MatchableQuestion(QuestionData questionData, List<MatchableAnswerOption> matchableAnswerOptionList) {
        super(questionData);
        this.matchableAnswerOptionList = matchableAnswerOptionList;
    }


    public MatchableQuestion(Long id, String question, QuestionType questionType, Double complexity, Set<UserFile> userFiles, Theme theme, Subject subject, List<MatchableAnswerOption> matchableAnswerOptionList) {
        super(id, question, questionType, complexity, userFiles, theme, subject);
        this.matchableAnswerOptionList = matchableAnswerOptionList;
    }

    public MatchableQuestion(List<MatchableAnswerOption> matchableAnswerOptionList) {
        this.matchableAnswerOptionList = matchableAnswerOptionList;
    }

    public List<MatchableAnswerOption> getMatchableAnswerOptionList() {
        return matchableAnswerOptionList;
    }

    public void setMatchableAnswerOptionList(List<MatchableAnswerOption> matchableAnswerOptionList) {
        this.matchableAnswerOptionList = matchableAnswerOptionList;
    }

}
