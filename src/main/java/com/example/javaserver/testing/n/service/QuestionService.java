package com.example.javaserver.testing.n.service;

import com.example.javaserver.testing.config.QuestionType;
import com.example.javaserver.testing.n.dto.mapper.CustomQuestionMapper;
import com.example.javaserver.testing.n.dto.question.QuestionDataDto;
import com.example.javaserver.testing.n.dto.test.TestDto;
import com.example.javaserver.testing.n.model.QuestionData;
import com.example.javaserver.testing.n.model.SelectableQuestion;
import com.example.javaserver.testing.n.repo.MatchableQuestionRepo;
import com.example.javaserver.testing.n.repo.SelectableQuestionRepo;
import com.example.javaserver.testing.n.repo.WriteableQuestionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {
    public CustomQuestionMapper customQuestionMapper;
    public SelectableQuestionRepo selectableQuestionRepo;
    public WriteableQuestionRepo writeableQuestionRepo;
    public MatchableQuestionRepo matchableQuestionRepo;

    @Autowired
    public QuestionService(CustomQuestionMapper customQuestionMapper, SelectableQuestionRepo selectableQuestionRepo, WriteableQuestionRepo writeableQuestionRepo, MatchableQuestionRepo matchableQuestionRepo) {
        this.customQuestionMapper = customQuestionMapper;
        this.selectableQuestionRepo = selectableQuestionRepo;
        this.writeableQuestionRepo = writeableQuestionRepo;
        this.matchableQuestionRepo = matchableQuestionRepo;
    }


    public void createQuestions(TestDto testDto) {
        for (QuestionDataDto questionDataDto : testDto.getQuestionDataDtoList()) {
            Object questionData = customQuestionMapper.toEntity(questionDataDto, testDto.getThemeId(), testDto.getSubjectId());

            System.out.println(questionData);
            if (((QuestionData) questionData).getQuestionType().equals(QuestionType.SELECT)) {
                SelectableQuestion question = new SelectableQuestion();
                question = (SelectableQuestion) questionData;
                selectableQuestionRepo.flush();
                selectableQuestionRepo.save(question);
            }
        }
    }

    private void createSelectableQuestion(QuestionDataDto questionDataDto) {
        SelectableQuestion question = new SelectableQuestion();

    }

}
