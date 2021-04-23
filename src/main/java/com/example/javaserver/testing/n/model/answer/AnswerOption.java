package com.example.javaserver.testing.n.model.answer;

import com.example.javaserver.study.model.UserFile;
import com.example.javaserver.testing.n.model.SelectableQuestion;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;

@Entity
public class AnswerOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String answer;
    private Boolean isRight;
    @OneToOne
    @JsonProperty("fileIds")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private UserFile file;

    @JsonProperty("questionId")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne
    private SelectableQuestion chooseableQuestion;

    public AnswerOption() {
    }

    public AnswerOption(Integer id, String answer, Boolean isRight, UserFile file) {
        this.id = id;
        this.answer = answer;
        this.isRight = isRight;
        this.file = file;
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

    public UserFile getFile() {
        return file;
    }

    public void setFile(UserFile file) {
        this.file = file;
    }
}
