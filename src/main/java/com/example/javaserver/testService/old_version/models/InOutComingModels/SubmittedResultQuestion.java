package com.example.javaserver.testService.old_version.models.InOutComingModels;



import com.example.javaserver.common_data.model.Subject;
import com.example.javaserver.testService.old_version.models.AnswerChoice;
import com.example.javaserver.testService.old_version.models.Question;
import com.example.javaserver.testService.old_version.models.UserAnswer;

import java.util.List;
import java.util.stream.Collectors;

public class SubmittedResultQuestion extends Question {
    private List<UserAnswer> userAnswer;
    private List<String> answers;
    private List<String> rightAnswers;
    public SubmittedResultQuestion(List<UserAnswer> userAnswer) {
        this.userAnswer = userAnswer;
    }

    public SubmittedResultQuestion(RequestedQuestion requestedQuestion, Subject subject, List<UserAnswer> userAnswer) {
        super(requestedQuestion, subject);
        this.userAnswer = userAnswer;
    }

    public SubmittedResultQuestion(Question question, List<UserAnswer> userAnswer) {
        super(question.getId(), question.getQuestion(), question.getComplexity());
        this.answers = question.getAnswerChoiceList().stream()
                .map(AnswerChoice::getAnswer).collect(Collectors.toList());
        this.rightAnswers = question.getAnswerChoiceList().stream()
                .filter(AnswerChoice::getRight)
                .map(AnswerChoice::getAnswer)
                .collect(Collectors.toList());
        this.userAnswer = userAnswer;
    }

    public List<UserAnswer> getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(List<UserAnswer> userAnswer) {
        this.userAnswer = userAnswer;
    }

    public List<String> getRightAnswers() {
        return rightAnswers;
    }

    public void setRightAnswers(List<String> rightAnswers) {
        this.rightAnswers = rightAnswers;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }




    //    public SubmittedResultQuestion(Question question, String userAnswer) {
//        super(question.getId(), question.getSubject(), question.getQuestion(),
//                question.getAnswers(),question.getRightAnswers(), question.getComplexity());
//        this.userAnswer = userAnswer;
//    }


}
