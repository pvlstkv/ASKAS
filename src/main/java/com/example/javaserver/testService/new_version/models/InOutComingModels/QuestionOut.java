package com.example.javaserver.testService.new_version.models.InOutComingModels;


import com.example.javaserver.testService.new_version.configs.QuestionType;
import com.example.javaserver.testService.new_version.models.AnswerChoice;
import com.example.javaserver.testService.new_version.models.Question;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.stream.Collectors;

public class QuestionOut {
    @ApiModelProperty(notes = "\"id\" field doesn't need, when an actor create a new questions. In other ways it is necessary.")
    private Integer id;
    private String question;
    private List<String> answers;
    private QuestionType questionType;

    public QuestionOut() {
    }

    public QuestionOut(Question question) {
        this.id = Math.toIntExact(question.getId());
        this.question = question.getQuestion();
        this.answers = question.getAnswerChoiceList().stream().
                map(AnswerChoice::getAnswer).collect(Collectors.toList());
    }

    // when actor updates a old question
    public QuestionOut(Long id, String question, List<String> answers, QuestionType questionType) {
        this.questionType = questionType;
        this.id = Math.toIntExact(id);
        this.question = question;
        this.answers = answers;
    }

    // when actor creates a new question
    public QuestionOut(String question, List<String> answers, QuestionType questionType) {
        this.questionType = questionType;
        this.question = question;
        this.answers = answers;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }
}
