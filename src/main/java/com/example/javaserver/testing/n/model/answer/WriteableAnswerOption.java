package com.example.javaserver.testing.n.model.answer;

import com.example.javaserver.testing.n.model.WriteQuestion;

import javax.persistence.*;

@Entity
public class WriteAnswerOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String answer;
    private Boolean isStrict;

    @ManyToOne
    private WriteQuestion writeQuestion;

    public WriteAnswerOption() {
    }

    public WriteAnswerOption(Integer id, String answer, Boolean isStrict) {
        this.id = id;
        this.answer = answer;

        this.isStrict = isStrict;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Boolean getStrict() {
        return isStrict;
    }

    public void setStrict(Boolean strict) {
        isStrict = strict;
    }
}
