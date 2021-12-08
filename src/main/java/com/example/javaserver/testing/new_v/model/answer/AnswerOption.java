package com.example.javaserver.testing.new_v.model.answer;

import com.example.javaserver.testing.new_v.model.question.SelectableQuestion;
import com.example.javaserver.study.model.UserFile;
import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;

@Entity
public class AnswerOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String answer;
    private Boolean isRight;
    @OneToOne
    @JsonProperty("fileIds")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private UserFile file;

    @JsonIgnore
    @ManyToOne
    private SelectableQuestion selectableQuestion;

    public AnswerOption() {
    }

    public AnswerOption(Long id, String answer, Boolean isRight, UserFile file) {
        this.id = id;
        this.answer = answer;
        this.isRight = isRight;
        this.file = file;
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

    public SelectableQuestion getSelectableQuestion() {
        return selectableQuestion;
    }

    public void setSelectableQuestion(SelectableQuestion selectableQuestion) {
        this.selectableQuestion = selectableQuestion;
    }
}
