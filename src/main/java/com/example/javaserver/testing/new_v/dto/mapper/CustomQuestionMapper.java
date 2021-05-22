package com.example.javaserver.testing.new_v.dto.mapper;

import com.example.javaserver.common_data.model.Subject;
import com.example.javaserver.common_data.repo.SubjectRepo;
import com.example.javaserver.testing.new_v.config.QuestionType;
import com.example.javaserver.testing.new_v.dto.answer.for_test.TestAnswerOptionDto;
import com.example.javaserver.testing.new_v.dto.answer.for_test.TestMatchableAnswerDto;
import com.example.javaserver.testing.theme.Theme;
import com.example.javaserver.testing.new_v.model.answer.MatchableAnswerOption;
import com.example.javaserver.testing.new_v.model.answer.WriteableAnswerOption;
import com.example.javaserver.testing.new_v.model.question.MatchableQuestion;
import com.example.javaserver.testing.new_v.model.question.QuestionData;
import com.example.javaserver.testing.new_v.model.question.SelectableQuestion;
import com.example.javaserver.testing.new_v.model.question.WriteableQuestion;
import com.example.javaserver.testing.theme.ThemeRepo;
import com.example.javaserver.study.model.UserFile;
import com.example.javaserver.study.service.UserFileService;

import com.example.javaserver.testing.new_v.dto.question.QuestionDataDto;
import com.example.javaserver.testing.new_v.model.answer.AnswerOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CustomQuestionMapper {
    private final UserFileService userFileService;
    private final SubjectRepo subjectRepo;
    private final ThemeRepo themeRepo;


    @Autowired
    public CustomQuestionMapper(UserFileService userFileService, SubjectRepo subjectRepo, ThemeRepo themeRepo) {
        this.userFileService = userFileService;
        this.subjectRepo = subjectRepo;
        this.themeRepo = themeRepo;
    }

    public QuestionData toEntity(QuestionDataDto question, Long themeId, Long subjectId) {
        Set<UserFile> files = userFileService.getByIds(question.getFileIds());
        Optional<Subject> subject = Optional.of(subjectRepo.findById(subjectId).orElse(new Subject()));
        Optional<Theme> theme = Optional.of(themeRepo.findById(themeId).orElse(new Theme()));
        QuestionData questionData = new QuestionData(question.getId(), question.getQuestion(), question.getQuestionType(),
                question.getComplexity(), files, theme.get(), subject.get());
        if (question.getQuestionType().equals(QuestionType.SELECT) || question.getQuestionType().equals(QuestionType.SEQUENCE)) {
            SelectableQuestion selectableQuestion = new SelectableQuestion(questionData);
            List<AnswerOption> answers = extractSelectableAnswerOptionList((ArrayList<Map<String, Object>>) question.getAnswers(), selectableQuestion);
            selectableQuestion.setAnswerOptionList(answers);
            return selectableQuestion;
        } else if (question.getQuestionType().equals(QuestionType.WRITE)) {
            WriteableQuestion writeableQuestion = new WriteableQuestion(questionData);
            List<WriteableAnswerOption> answers = extractWriteableAnswerOptionList((ArrayList<Map<String, Object>>) question.getAnswers(), writeableQuestion);
            writeableQuestion.setWriteableAnswerOptionList(answers);
            return writeableQuestion;
        } else {
            MatchableQuestion matchableQuestion = new MatchableQuestion(questionData);
            List<MatchableAnswerOption> answers = extractMatchableAnswerOptionList((ArrayList<Map<String, Object>>) question.getAnswers(), matchableQuestion);
            matchableQuestion.setMatchableAnswerOptionList(answers);
            return matchableQuestion;
        }
    }

    public QuestionDataDto toDto(QuestionData questionData) {
        QuestionDataDto questionDataDto = new QuestionDataDto();
        questionDataDto.setId(questionData.getId());
        questionDataDto.setQuestion(questionData.getQuestion());
        questionDataDto.setFileIds(questionData.getUserFiles().stream().map(UserFile::getId).collect(Collectors.toSet()));
        questionDataDto.setQuestionType(questionData.getQuestionType());
        if (questionData.getQuestionType().equals(QuestionType.WRITE)) {
            return questionDataDto;
        } else if (questionData.getQuestionType().equals(QuestionType.SELECT) || questionData.getQuestionType().equals(QuestionType.SEQUENCE)) {
            List<TestAnswerOptionDto> selectableAnswerDtos = new ArrayList<>();
            for (AnswerOption answer : ((SelectableQuestion) questionData).getAnswerOptionList()) {
                selectableAnswerDtos.add(new TestAnswerOptionDto(answer.getId(), answer.getAnswer(), getFileId(answer.getFile())));
            }
            questionDataDto.setAnswers(selectableAnswerDtos);
        } else { //if (questionData.getQuestionType().equals(QuestionType.MATCH))
            List<TestMatchableAnswerDto> matchableAnswerDtos = new ArrayList<>();
            for (MatchableAnswerOption answer : ((MatchableQuestion) questionData).getMatchableAnswerOptionList()) {
                TestAnswerOptionDto key = new TestAnswerOptionDto(answer.getKey().getId(), answer.getKey().getAnswer(), getFileId(answer.getKey().getFile()));
                TestAnswerOptionDto value = new TestAnswerOptionDto(answer.getValue().getId(), answer.getValue().getAnswer(), getFileId(answer.getValue().getFile()));
                matchableAnswerDtos.add(new TestMatchableAnswerDto(key, value));
            }
            questionDataDto.setAnswers(matchableAnswerDtos);
        }
        return questionDataDto;
    }

    private Long getFileId(UserFile file) {
        return file != null ? file.getId() : null;
    }

    private List<AnswerOption> extractSelectableAnswerOptionList(ArrayList<Map<String, Object>> answers, SelectableQuestion selectableQuestion) {
        List<AnswerOption> fetchedAnswers = new ArrayList<>();
        for (Map<String, Object> pair : answers) {
            AnswerOption answerOption = extractAnswerOption(pair);
            answerOption.setSelectableQuestion(selectableQuestion);
            fetchedAnswers.add(answerOption);
        }
        return fetchedAnswers;
    }

    private AnswerOption extractAnswerOption(Map<String, Object> pair) {
        Integer pairId = (Integer) pair.get("fileId");
        Long fileId = null;
        UserFile file = null;
        if (pairId != null) {
            fileId = pairId.longValue();
            file = userFileService.getById(fileId);
        }
        pairId = (Integer) pair.get("id");
        Long id = null;
        if (pairId != null) {
            id = pairId.longValue();
        }
        return new AnswerOption(id, (String) pair.get("answer"), (Boolean) pair.get("isRight"), file);
    }

    private List<WriteableAnswerOption> extractWriteableAnswerOptionList(ArrayList<Map<String, Object>> answers, WriteableQuestion writeableQuestion) {
        List<WriteableAnswerOption> fetchedAnswers = new ArrayList<>();
        for (Map<String, Object> pair : answers) {
            Integer pairId = (Integer) pair.get("id");
            Long id = null;
            if (pairId != null) {
                id = pairId.longValue();
            }
            WriteableAnswerOption answerOption = new WriteableAnswerOption(id, ((String) pair.get("answer")).trim(), (Boolean) pair.get("isStrict"));
            answerOption.setWriteableQuestion(writeableQuestion);
            fetchedAnswers.add(answerOption);
        }
        return fetchedAnswers;
    }

    private List<MatchableAnswerOption> extractMatchableAnswerOptionList(ArrayList<Map<String, Object>> answers, MatchableQuestion matchableQuestion) {
        List<MatchableAnswerOption> fetchedAnswers = new ArrayList<>();
        for (Map<String, Object> pair : answers) {
            Map<String, Object> keyMap = (Map<String, Object>) pair.get("key");
            AnswerOption keyAnswerOption = extractAnswerOption(keyMap);

            Map<String, Object> valueMap = (Map<String, Object>) pair.get("value");
            AnswerOption valueAnswerOption = extractAnswerOption(valueMap);

            Integer pairId = (Integer) pair.get("id");
            Long id = null;
            if (pairId != null) {
                id = pairId.longValue();
            }

            MatchableAnswerOption answerOption = new MatchableAnswerOption(id, keyAnswerOption, valueAnswerOption, matchableQuestion);
            fetchedAnswers.add(answerOption);
        }
        return fetchedAnswers;
    }
}
