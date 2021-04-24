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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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


    @Transactional

    public void saveQuestions(TestDto testDto) {
        for (QuestionDataDto questionDataDto : testDto.getQuestionDataDtoList()) {
            QuestionData questionData = customQuestionMapper.toEntity(questionDataDto, testDto.getThemeId(), testDto.getSubjectId());
            System.out.println(questionData);
//            if (questionData.getQuestionType().equals(QuestionType.SELECT)) {
                questionRepo.save(questionData);
//            }
        }
    }

    public void deleteQuestions(Set<Long> ids){
        questionRepo.deleteAllByIdIn(ids);
    }

    public QuestionData get(Long id){
        Optional<QuestionData> questionData =  questionRepo.findById(id);
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
