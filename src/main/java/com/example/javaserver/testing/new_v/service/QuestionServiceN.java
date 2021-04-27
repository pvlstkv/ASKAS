package com.example.javaserver.testing.new_v.service;

import com.example.javaserver.testing.new_v.repo.question.MatchableQuestionRepo;
import com.example.javaserver.testing.new_v.repo.question.QuestionDataRepo;
import com.example.javaserver.testing.new_v.repo.question.SelectableQuestionRepo;
import com.example.javaserver.testing.new_v.repo.question.WriteableQuestionRepo;
import com.example.javaserver.testing.new_v.dto.mapper.CustomQuestionMapper;
import com.example.javaserver.testing.new_v.dto.question.QuestionDataDto;
import com.example.javaserver.testing.new_v.dto.test.TestDto;
import com.example.javaserver.testing.new_v.model.question.QuestionData;
import com.example.javaserver.testing.new_v.model.question.SelectableQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;

@Service
public class QuestionServiceN {

    private CustomQuestionMapper customQuestionMapper;
    private SelectableQuestionRepo selectableQuestionRepo;
    private WriteableQuestionRepo writeableQuestionRepo;
    private MatchableQuestionRepo matchableQuestionRepo;
    private QuestionDataRepo questionDataRepo;

    @Autowired
    public QuestionServiceN(CustomQuestionMapper customQuestionMapper, SelectableQuestionRepo selectableQuestionRepo, WriteableQuestionRepo writeableQuestionRepo, MatchableQuestionRepo matchableQuestionRepo, QuestionDataRepo questionDataRepo) {
        this.customQuestionMapper = customQuestionMapper;
        this.selectableQuestionRepo = selectableQuestionRepo;
        this.writeableQuestionRepo = writeableQuestionRepo;
        this.matchableQuestionRepo = matchableQuestionRepo;
        this.questionDataRepo = questionDataRepo;
    }


    @Transactional

    public void saveQuestions(TestDto testDto) {
        for (QuestionDataDto questionDataDto : testDto.getQuestionDataDtoList()) {
            QuestionData questionData = customQuestionMapper.toEntity(questionDataDto, testDto.getThemeId(), testDto.getSubjectId());
            System.out.println(questionData);
//            if (questionData.getQuestionType().equals(QuestionType.SELECT)) {
                questionDataRepo.save(questionData);
//            }
        }
    }

    public void deleteQuestions(Set<Long> ids){
        questionDataRepo.deleteAllByIdIn(ids);
    }

    public QuestionData get(Long id){
        Optional<QuestionData> questionData =  questionDataRepo.findById(id);
        if (questionData.isPresent()){
            return questionData.get();
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "вопроса с id = " + id + "не существует");
        }

    }
    private void createSelectableQuestion(QuestionDataDto questionDataDto) {
        SelectableQuestion question = new SelectableQuestion();

    }

}
