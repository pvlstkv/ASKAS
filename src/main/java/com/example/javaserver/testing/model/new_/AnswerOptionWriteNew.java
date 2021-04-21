package com.example.javaserver.testing.model.new_;

import javax.persistence.*;

@Entity
public class AnswerOptionWriteNew {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String answer;
    private Boolean isRight;
    private Boolean isStrict;

    @ManyToOne
    private QuestionWriteNew questionWriteNew;

    public AnswerOptionWriteNew() {
    }

    public AnswerOptionWriteNew(Integer id, String answer, Boolean isRight, Boolean isStrict) {
        this.id = id;
        this.answer = answer;
        this.isRight = isRight;
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

    public Boolean getRight() {
        return isRight;
    }

    public void setRight(Boolean right) {
        isRight = right;
    }

    public Boolean getStrict() {
        return isStrict;
    }

    public void setStrict(Boolean strict) {
        isStrict = strict;
    }
}
