package com.example.javaserver.testing.new_v.dto.mapper;

import com.example.javaserver.testing.new_v.config.QuestionType;
import com.example.javaserver.testing.new_v.dto.answer.for_test.result.*;
import com.example.javaserver.testing.new_v.model.question.QuestionData;
import com.example.javaserver.testing.new_v.model.saving_result.PassedTestN;
import com.example.javaserver.testing.new_v.model.saving_result.answer.PassedMatchableAnswer;
import com.example.javaserver.testing.new_v.model.saving_result.answer.PassedSelectableAnswer;
import com.example.javaserver.testing.new_v.model.saving_result.answer.PassedWriteableAnswer;
import com.example.javaserver.testing.new_v.model.saving_result.question.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResultMapper {

    public List<AfterCheckTestDto> toDto(List<PassedTestN> passedTestList) {
        List<AfterCheckTestDto> tests = new ArrayList<>();
        for (PassedTestN passedTest : passedTestList) {
            List<AfterCheckQuestionDto> questionDtos = new ArrayList<>();
            for (PassedQuestionData passedQuestion : passedTest.getPassedQuestions()) {
                Object userAnswers = extractUserAnswers(passedQuestion);
                addQuestion(passedQuestion.getQuestionData(), userAnswers, questionDtos);
            }
            tests.add(new AfterCheckTestDto(questionDtos, passedTest.getRatingInPercent(), passedTest.getPassedAt()));
        }
        return tests;
    }

    private Object extractUserAnswers(PassedQuestionData passedQuestion) {
        Object userAnswers = null;
        if (passedQuestion.getQuestionData().getQuestionType().equals(QuestionType.SELECT)) {
            userAnswers = ((PassedSelectableQuestion) passedQuestion).getUserAnswers();
            List<UserSelectableAnswer> selectableAnswers = new ArrayList<>();
            ((List<PassedSelectableAnswer>) userAnswers).forEach(it -> selectableAnswers.add(
                    new UserSelectableAnswer(it.getId(), it.getRight())
            ));
            return selectableAnswers;
        } else if (passedQuestion.getQuestionData().getQuestionType().equals(QuestionType.WRITE)) {
            userAnswers = ((PassedWriteableQuestion) passedQuestion).getUserAnswers();
            List<UserWriteableAnswer> writeableAnswers = new ArrayList<>();
            ((List<PassedWriteableAnswer>) userAnswers).forEach(it -> writeableAnswers.add(
                    new UserWriteableAnswer(it.getUserAnswer(), it.getRight())
            ));
            return writeableAnswers;
        } else if (passedQuestion.getQuestionData().getQuestionType().equals(QuestionType.MATCH)) {
            userAnswers = ((PassedMatchableQuestion) passedQuestion).getUserAnswers();
            List<UserMatchableAnswer> matchableAnswers = new ArrayList<>();
            ((List<PassedMatchableAnswer>) userAnswers).forEach(it -> matchableAnswers.add(
                    new UserMatchableAnswer(it.getKey().getId(), it.getValue().getId(), it.getRight())
            ));
            return matchableAnswers;
        }
        return userAnswers;
    }

    private void addQuestion(QuestionData questionData, Object userAnswers, List<AfterCheckQuestionDto> questionDtos) {
        questionDtos.add(new AfterCheckQuestionDto(questionData, userAnswers));
    }
}
