package com.example.javaserver.testing.n.model.answer;

import com.example.javaserver.testing.n.model.WriteableQuestion;

import javax.persistence.*;

@Entity
public class WriteableAnswerOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String answer;
    private Boolean isStrict;

    @ManyToOne
    private WriteableQuestion writeableQuestion;

    public WriteableAnswerOption() {
    }

    public WriteableAnswerOption(Long id, String answer, Boolean isStrict) {
        this.id = id;
        this.answer = answer;
        this.isStrict = isStrict;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public WriteableQuestion getWriteableQuestion() {
        return writeableQuestion;
    }

    public void setWriteableQuestion(WriteableQuestion writeableQuestion) {
        this.writeableQuestion = writeableQuestion;
    }
}
