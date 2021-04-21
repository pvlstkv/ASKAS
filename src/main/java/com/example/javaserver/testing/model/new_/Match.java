package com.example.javaserver.testing.model.new_;

import javax.persistence.*;

@Entity
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne
    private AnswerOptionNew key;
    @OneToOne
    private AnswerOptionNew value;
    @ManyToOne
    private QuestionMatchNew questionMatchNew;

    public Match() {
    }

    public Match(Integer id, AnswerOptionNew key, AnswerOptionNew value) {
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

    public AnswerOptionNew getKey() {
        return key;
    }

    public void setKey(AnswerOptionNew key) {
        this.key = key;
    }

    public AnswerOptionNew getValue() {
        return value;
    }

    public void setValue(AnswerOptionNew value) {
        this.value = value;
    }
}
