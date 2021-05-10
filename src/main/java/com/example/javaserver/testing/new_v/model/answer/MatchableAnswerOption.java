package com.example.javaserver.testing.new_v.model.answer;


import com.example.javaserver.testing.new_v.model.question.MatchableQuestion;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "matchable_answer_options")
public class MatchableAnswerOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    private AnswerOption key;
    @OneToOne(cascade = CascadeType.ALL)
    private AnswerOption value;

    @ManyToOne
    @JsonIgnore
    private MatchableQuestion matchableQuestion;

    public MatchableAnswerOption() {
    }

    public MatchableAnswerOption(Long id, AnswerOption key, AnswerOption value, MatchableQuestion matchableQuestion) {
        this.id = id;
        this.key = key;
        this.value = value;
        this.matchableQuestion = matchableQuestion;
    }

    public MatchableAnswerOption(Long id, AnswerOption key, AnswerOption value) {
        this.id = id;
        this.key = key;
        this.value = value;
    }

    public MatchableAnswerOption( AnswerOption key, AnswerOption value) {
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

    public MatchableQuestion getMatchableQuestion() {
        return matchableQuestion;
    }

    public void setMatchableQuestion(MatchableQuestion matchableQuestion) {
        this.matchableQuestion = matchableQuestion;
    }
}
