package com.example.javaserver.testing.new_v.dto.question;

import com.example.javaserver.testing.new_v.config.QuestionType;
import com.example.javaserver.testing.new_v.dto.answer.MatchableAnswerDto;

import java.util.List;
import java.util.Set;

public class MatchableQuestionDto extends QuestionDataDto{
    private List<MatchableAnswerDto> matchableAnswerList;
    public MatchableQuestionDto(String question, QuestionType questionType, Double complexity, Set<Long> fileIds) {
        super(question, questionType, complexity, fileIds);
    }

    public MatchableQuestionDto(String question, QuestionType questionType, Double complexity, Set<Long> fileIds, List<MatchableAnswerDto> matchableAnswerList) {
        super(question, questionType, complexity, fileIds);
        this.matchableAnswerList = matchableAnswerList;
    }

    public List<MatchableAnswerDto> getMatchableAnswerList() {
        return matchableAnswerList;
    }

    public void setMatchableAnswerList(List<MatchableAnswerDto> matchableAnswerList) {
        this.matchableAnswerList = matchableAnswerList;
    }
}
