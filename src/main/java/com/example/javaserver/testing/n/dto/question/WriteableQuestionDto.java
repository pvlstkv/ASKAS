package com.example.javaserver.testing.n.dto.question;

import com.example.javaserver.testing.n.config.QuestionType;
import com.example.javaserver.testing.n.dto.answer.WriteableAnswerDto;

import java.util.Set;

public class WriteableQuestionDto extends QuestionDataDto{
    private WriteableAnswerDto writeableAnswer;
    public WriteableQuestionDto(String question, QuestionType questionType, Double complexity, Set<Long> fileIds) {
        super(question, questionType, complexity, fileIds);
    }

    public WriteableQuestionDto(String question, QuestionType questionType, Double complexity, Set<Long> fileIds, WriteableAnswerDto answer) {
        super(question, questionType, complexity, fileIds);
        this.writeableAnswer = answer;
    }

    public WriteableAnswerDto getWriteableAnswer() {
        return writeableAnswer;
    }

    public void setWriteableAnswer(WriteableAnswerDto writeableAnswer) {
        this.writeableAnswer = writeableAnswer;
    }
}
