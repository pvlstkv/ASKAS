package com.example.javaserver.testing.n.dto.mapper;

import com.example.javaserver.common_data.model.Subject;
import com.example.javaserver.common_data.repo.SubjectRepo;
import com.example.javaserver.study.model.UserFile;
import com.example.javaserver.study.service.UserFileService;
import com.example.javaserver.testing.config.QuestionType;
import com.example.javaserver.testing.model.Theme;
import com.example.javaserver.testing.n.dto.question.QuestionDataDto;
import com.example.javaserver.testing.n.model.MatchableQuestion;
import com.example.javaserver.testing.n.model.QuestionData;
import com.example.javaserver.testing.n.model.SelectableQuestion;
import com.example.javaserver.testing.n.model.WriteableQuestion;
import com.example.javaserver.testing.n.model.answer.AnswerOption;
import com.example.javaserver.testing.n.model.answer.MatchableAnswerOption;
import com.example.javaserver.testing.n.model.answer.WriteableAnswerOption;
import com.example.javaserver.testing.repo.ThemeRepo;
import com.example.javaserver.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
            writeableQuestion.setAnswerOptionWriteList(answers);
            return writeableQuestion;
        } else {
            MatchableQuestion matchableQuestion = new MatchableQuestion(questionData);
            List<MatchableAnswerOption> answers = extractMatchableAnswerOptionList((ArrayList<Map<String, Object>>) question.getAnswers(), matchableQuestion);
            matchableQuestion.setMatchableAnswerOptionList(answers);
            return matchableQuestion;
        }
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
            WriteableAnswerOption answerOption = new WriteableAnswerOption(id, (String) pair.get("answer"), (Boolean) pair.get("isStrict"));
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
