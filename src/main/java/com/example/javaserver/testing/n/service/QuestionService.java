package com.example.javaserver.testing.n.service;

import com.example.javaserver.testing.config.QuestionType;
import com.example.javaserver.testing.n.dto.mapper.CustomQuestionMapper;
import com.example.javaserver.testing.n.dto.question.QuestionDataDto;
import com.example.javaserver.testing.n.dto.test.TestDto;
import com.example.javaserver.testing.n.model.QuestionData;
import com.example.javaserver.testing.n.model.SelectableQuestion;
import com.example.javaserver.testing.n.repo.MatchableQuestionRepo;
import com.example.javaserver.testing.n.repo.QuestionRepo;
import com.example.javaserver.testing.n.repo.SelectableQuestionRepo;
import com.example.javaserver.testing.n.repo.WriteableQuestionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {

    private CustomQuestionMapper customQuestionMapper;
    private SelectableQuestionRepo selectableQuestionRepo;
    private WriteableQuestionRepo writeableQuestionRepo;
    private MatchableQuestionRepo matchableQuestionRepo;
    private QuestionRepo questionRepo;

    @Autowired
    public QuestionService(CustomQuestionMapper customQuestionMapper, SelectableQuestionRepo selectableQuestionRepo, WriteableQuestionRepo writeableQuestionRepo, MatchableQuestionRepo matchableQuestionRepo, QuestionRepo questionRepo) {
        this.customQuestionMapper = customQuestionMapper;
        this.selectableQuestionRepo = selectableQuestionRepo;
        this.writeableQuestionRepo = writeableQuestionRepo;
        this.matchableQuestionRepo = matchableQuestionRepo;
        this.questionRepo = questionRepo;
    }


    public void createQuestions(TestDto testDto) {
        for (QuestionDataDto questionDataDto : testDto.getQuestionDataDtoList()) {
            QuestionData questionData = customQuestionMapper.toEntity(questionDataDto, testDto.getThemeId(), testDto.getSubjectId());
            System.out.println(questionData);
//            if (questionData.getQuestionType().equals(QuestionType.SELECT)) {
                questionRepo.save(questionData);
//            }
        }
    }

    public QuestionData get(Long id){
        return questionRepo.findById(id).get();

    }
    private void createSelectableQuestion(QuestionDataDto questionDataDto) {
        SelectableQuestion question = new SelectableQuestion();

    }

}
