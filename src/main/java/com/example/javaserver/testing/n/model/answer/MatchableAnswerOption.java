package com.example.javaserver.testing.n.model.answer;

import com.example.javaserver.testing.n.model.MatchableQuestion;

import javax.persistence.*;

@Entity
public class MatchableAnswerOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private AnswerOption key;
    @OneToOne
    private AnswerOption value;
    @ManyToOne
    private MatchableQuestion matchableQuestion;

    public MatchableAnswerOption() {
    }

    public MatchableAnswerOption(Long id, AnswerOption key, AnswerOption value) {
        this.id = id;
        this.key = key;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AnswerOption getKey() {
        return key;
    }

    public void setKey(AnswerOption key) {
        this.key = key;
    }

    public AnswerOption getValue() {
        return value;
    }

    public void setValue(AnswerOption value) {
        this.value = value;
    }

    public MatchableQuestion getQuestionMatch() {
        return matchableQuestion;
    }

    public void setQuestionMatch(MatchableQuestion matchableQuestion) {
        this.matchableQuestion = matchableQuestion;
    }
}
