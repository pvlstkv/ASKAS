package com.example.javaserver.testing.n.model.answer;

import com.example.javaserver.testing.n.model.MatchQuestion;

import javax.persistence.*;

@Entity
public class MatchAnswerOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne
    private AnswerOption key;
    @OneToOne
    private AnswerOption value;
    @ManyToOne
    private MatchQuestion matchQuestion;

    public MatchAnswerOption() {
    }

    public MatchAnswerOption(Integer id, AnswerOption key, AnswerOption value) {
        this.id = id;
        this.key = key;
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public MatchQuestion getQuestionMatch() {
        return matchQuestion;
    }

    public void setQuestionMatch(MatchQuestion matchQuestion) {
        this.matchQuestion = matchQuestion;
    }
}
