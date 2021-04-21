//package com.example.javaserver.testing.model.dto;
//
//
//import com.example.javaserver.testing.config.QuestionType;
//import com.example.javaserver.testing.model.AnswerChoice;
//import com.example.javaserver.testing.model.Question;
//import io.swagger.annotations.ApiModelProperty;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class QuestionOut {
//    @ApiModelProperty(notes = "\"id\" field doesn't need, when an actor create a new questions. In other ways it is necessary.")
//    private Long id;
//    private String question;
//    private List<String> answers;
//    private QuestionType questionType;
//    private List<Long> fileIds;
//
//    public QuestionOut() {
//    }
//
//    public QuestionOut(Question question) {
//        this.id = question.getId();
//        this.question = question.getQuestion();
//        this.questionType = question.getQuestionType();
//        this.fileIds = question.fetchUserFilesIds();
//        if (questionType != QuestionType.WRITE) {
//            this.answers = question.getAnswerChoiceList().stream().
//                    map(AnswerChoice::getAnswer).collect(Collectors.toList());
//        }
//    }
//
//    // when actor updates a old question
//    public QuestionOut(Long id, String question, List<String> answers, QuestionType questionType, List<Long> fileIds) {
//        this.questionType = questionType;
//        this.id = id;
//        this.fileIds = fileIds;
//        this.question = question;
//        this.answers = answers;
//    }
//
//    // when actor creates a new question
//    public QuestionOut(String question, List<String> answers, QuestionType questionType, List<Long> fileIds) {
//        this.questionType = questionType;
//        this.fileIds = fileIds;
//        this.question = question;
//        this.answers = answers;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getQuestion() {
//        return question;
//    }
//
//    public void setQuestion(String question) {
//        this.question = question;
//    }
//
//    public List<String> getAnswers() {
//        return answers;
//    }
//
//    public void setAnswers(List<String> answers) {
//        this.answers = answers;
//    }
//
//    public QuestionType getQuestionType() {
//        return questionType;
//    }
//
//    public void setQuestionType(QuestionType questionType) {
//        this.questionType = questionType;
//    }
//
//    public List<Long> getFileIds() {
//        return fileIds;
//    }
//
//    public void setFileIds(List<Long> fileIds) {
//        this.fileIds = fileIds;
//    }
//}
