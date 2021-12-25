package com.example.javaserver.testing.new_v.service;

import com.example.javaserver.testing.new_v.config.QuestionType;
import com.example.javaserver.testing.new_v.model.question.MatchableQuestion;
import com.example.javaserver.testing.new_v.model.question.WriteableQuestion;
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
import java.text.MessageFormat;
import java.util.List;
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
            questionDataRepo.save(questionData);
        }
    }

    @Transactional
    public void updateQuestions(TestDto testDto) {
        for (QuestionDataDto questionDataDto : testDto.getQuestionDataDtoList()) {
            QuestionData questionData = customQuestionMapper.toEntity(questionDataDto, testDto.getThemeId(), testDto.getSubjectId());
            Optional<QuestionData> optionalQuestionData = questionDataRepo.findById(questionData.getId());
            if (optionalQuestionData.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        MessageFormat.format("Вопроса с id {0} не существует", questionData.getId()));
            }
            QuestionData questionDataFromDb = optionalQuestionData.get();
            if (!questionDataFromDb.getQuestionType().equals(questionData.getQuestionType())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        MessageFormat.format("Тип вопроса с id {0} не совпадает с имееющимся в бд {1}",
                                questionData.getId(), questionDataFromDb.getQuestionType().toString()));
            }
            updateFields(questionData, questionDataFromDb);
            questionDataRepo.save(questionDataFromDb);
        }
    }

    private void updateFields(QuestionData questionData, QuestionData questionDataFromDb) {
        questionDataFromDb.setQuestion(questionData.getQuestion());
        questionDataFromDb.setQuestionType(questionData.getQuestionType());
        questionDataFromDb.setComplexity(questionData.getComplexity());
        questionDataFromDb.setSubject(questionData.getSubject());
        questionDataFromDb.setTheme(questionData.getTheme());

        if (questionData.getQuestionType().equals(QuestionType.SELECT)
                || questionData.getQuestionType().equals(QuestionType.SEQUENCE)) {
            SelectableQuestion selectableQuestion = (SelectableQuestion) questionDataFromDb;
            selectableQuestion.putNewAnswers(((SelectableQuestion) questionData).getAnswerOptionList());

        } else if (questionData.getQuestionType().equals(QuestionType.MATCH)) {
            MatchableQuestion matchableQuestion = (MatchableQuestion) questionDataFromDb;
            matchableQuestion.putNewAnswers(((MatchableQuestion) questionData).getMatchableAnswerOptionList());

        } else { // WRITE type
            WriteableQuestion writeableQuestion = (WriteableQuestion) questionDataFromDb;
            writeableQuestion.putNewAnswers(((WriteableQuestion) questionData).getWriteableAnswerOptionList());
        }
    }

    public void deleteQuestions(Set<Long> ids) {
        questionDataRepo.deleteAllByIdIn(ids);
    }

    public QuestionData get(Long id) {
        Optional<QuestionData> questionData = questionDataRepo.findById(id);
        if (questionData.isPresent()) {
            return questionData.get();
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "вопроса с id = " + id + "не существует");
        }
    }

    public List<QuestionData> getByThemeId(Long themeId) {
        var questions = questionDataRepo.findAllByThemeId(themeId);
        return questions;
    }
}
