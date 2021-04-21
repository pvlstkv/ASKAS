package com.example.javaserver.testing.model.new_;

import javax.persistence.*;

@Entity
public class AnswerOptionWriteNew {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String answer;
    private Boolean isStrict;

    @ManyToOne
    private QuestionWriteNew questionWriteNew;

    public AnswerOptionWriteNew() {
    }

    public AnswerOptionWriteNew(Integer id, String answer, Boolean isStrict) {
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
