package com.example.javaserver.testing.model.new_;

import com.example.javaserver.common_data.model.Subject;
import com.example.javaserver.study.model.UserFile;
import com.example.javaserver.testing.config.QuestionType;
import com.example.javaserver.testing.model.Theme;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Set;

@Entity
@DiscriminatorValue("write")
public class QuestionWriteNew extends QuestionNew {
    @JsonProperty("fileIds")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @OneToMany(mappedBy = "questionWriteNew",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AnswerOptionWriteNew> answerOptionWriteNewList;

    public QuestionWriteNew() {
    }

    public QuestionWriteNew(Long id, String question, QuestionType questionType, Double complexity, Set<UserFile> userFiles, Theme theme, Subject subject, List<AnswerOptionWriteNew> answerOptionWriteNewList) {
        super(id, question, questionType, complexity, userFiles, theme, subject);
        this.answerOptionWriteNewList = answerOptionWriteNewList;
    }

    public QuestionWriteNew(List<AnswerOptionWriteNew> answerOptionWriteNewList) {
        this.answerOptionWriteNewList = answerOptionWriteNewList;
    }


    public List<AnswerOptionWriteNew> getAnswerOptionWriteNewList() {
        return answerOptionWriteNewList;
    }

    public void setAnswerOptionWriteNewList(List<AnswerOptionWriteNew> answerOptionWriteNewList) {
        this.answerOptionWriteNewList = answerOptionWriteNewList;
    }
}
