package com.example.javaserver.testing.n.model;

import com.example.javaserver.common_data.model.Subject;
import com.example.javaserver.study.model.UserFile;
import com.example.javaserver.testing.config.QuestionType;
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
import java.util.List;
import java.util.Set;
@Entity
@DiscriminatorValue("match")
public class MatchableQuestion extends QuestionData {

    @JsonProperty("answerOptions")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @OneToMany(mappedBy = "questionMatch",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MatchableAnswerOption> matchableAnswerOptionList;

    public MatchableQuestion() {
    }

    public MatchableQuestion(Long id, String question, QuestionType questionType, Double complexity, Set<UserFile> userFiles, Theme theme, Subject subject, List<MatchableAnswerOption> matchableAnswerOptionList) {
        super(id, question, questionType, complexity, userFiles, theme, subject);
        this.matchableAnswerOptionList = matchableAnswerOptionList;
    }

    public MatchableQuestion(List<MatchableAnswerOption> matchableAnswerOptionList) {
        this.matchableAnswerOptionList = matchableAnswerOptionList;
    }

    public List<MatchableAnswerOption> getMatchList() {
        return matchableAnswerOptionList;
    }

    public void setMatchList(List<MatchableAnswerOption> matchableAnswerOptionList) {
        this.matchableAnswerOptionList = matchableAnswerOptionList;
    }
}
