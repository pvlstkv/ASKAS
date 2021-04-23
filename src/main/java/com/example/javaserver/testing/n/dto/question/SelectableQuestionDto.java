package com.example.javaserver.testing.n.dto.question;

import com.example.javaserver.testing.config.QuestionType;
import com.example.javaserver.testing.n.dto.answer.AnswerOptionDto;

import java.util.List;
import java.util.Set;

public class SelectableQuestionDto extends QuestionDataDto {

    private List<AnswerOptionDto> selectableAnswerList;

    public SelectableQuestionDto(String question, QuestionType questionType, Double complexity, Set<Long> fileIds) {
        super(question, questionType, complexity, fileIds);
    }

    public SelectableQuestionDto(String question, QuestionType questionType, Double complexity, Set<Long> fileIds, List<AnswerOptionDto> answerList) {
        super(question, questionType, complexity, fileIds);
        this.selectableAnswerList = answerList;
    }

    public List<AnswerOptionDto> getSelectableAnswerList() {
        return selectableAnswerList;
    }

    public void setSelectableAnswerList(List<AnswerOptionDto> selectableAnswerList) {
        this.selectableAnswerList = selectableAnswerList;
    }
}
