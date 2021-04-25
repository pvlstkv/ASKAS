package com.example.javaserver.testing.n.model;

import com.example.javaserver.common_data.model.Subject;
import com.example.javaserver.study.model.UserFile;
import com.example.javaserver.testing.n.config.QuestionType;
import com.example.javaserver.testing.model.Theme;
import com.example.javaserver.testing.n.model.answer.WriteableAnswerOption;
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
public class WriteableQuestion extends QuestionData {

    @OneToMany(mappedBy = "writeableQuestion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WriteableAnswerOption> writeableAnswerOptionList;

    public WriteableQuestion() {
    }

    public WriteableQuestion(Long id, String question, QuestionType questionType, Double complexity, Set<UserFile> userFiles, Theme theme, Subject subject, List<WriteableAnswerOption> writeableAnswerOptionList) {
        super(id, question, questionType, complexity, userFiles, theme, subject);
        this.writeableAnswerOptionList = writeableAnswerOptionList;
    }

    public WriteableQuestion(QuestionData questionData) {
        super(questionData);
    }

    public WriteableQuestion(QuestionData questionData, List<WriteableAnswerOption> writeableAnswerOptionList) {
        super(questionData);
        this.writeableAnswerOptionList = writeableAnswerOptionList;
    }

    public WriteableQuestion(List<WriteableAnswerOption> writeableAnswerOptionList) {
        this.writeableAnswerOptionList = writeableAnswerOptionList;
    }


    public List<WriteableAnswerOption> getAnswerOptionWriteList() {
        return writeableAnswerOptionList;
    }

    public void setAnswerOptionWriteList(List<WriteableAnswerOption> writeableAnswerOptionList) {
        this.writeableAnswerOptionList = writeableAnswerOptionList;
    }
}
