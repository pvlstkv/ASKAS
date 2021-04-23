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

import java.util.*;

public class CustomQuestionMapper {
    private static UserFileService userFileService;
    private static SubjectRepo subjectRepo;
    private static ThemeRepo themeRepo;

    public static QuestionData toEntity(QuestionDataDto question, Long themeId, Long subjectId) {
        Set<UserFile> files = userFileService.getByIds(question.getFileIds());
        Optional<Subject> subject = subjectRepo.findById(subjectId);
        Optional<Theme> theme = themeRepo.findById(themeId);
        QuestionData questionData = new QuestionData(question.getId(), question.getQuestion(), question.getQuestionType(),
                question.getComplexity(), files, theme.get(), subject.get());
        if (question.getQuestionType().equals(QuestionType.SELECT) || question.getQuestionType().equals(QuestionType.SEQUENCE)) {

            SelectableQuestion selectableQuestion = new SelectableQuestion(questionData,
                    extractSelectableAnswerOptionList((ArrayList<Map<String, Object>>) question.getAnswers()));
            return selectableQuestion;
        } else if (question.getQuestionType().equals(QuestionType.WRITE)) {
            WriteableQuestion writeableQuestion = new WriteableQuestion(questionData,
                    extractWriteableAnswerOptionList((ArrayList<Map<String, Object>>) question.getAnswers()));
            return writeableQuestion;
        }else{
            MatchableQuestion matchableQuestion = new MatchableQuestion(questionData,
                    extractMatchableAnswerOptionList((ArrayList<Map<String, Object>>) question.getAnswers()));
            return matchableQuestion;
        }
    }

    private static List<AnswerOption> extractSelectableAnswerOptionList(ArrayList<Map<String, Object>> answers) {
        List<AnswerOption> fetchedAnswers = new ArrayList<>();
        for (Map<String, Object> pair : answers) {
            AnswerOption answerOption = extractAnswerOption(pair);
            fetchedAnswers.add(answerOption);
        }
        return fetchedAnswers;
    }

    private static AnswerOption extractAnswerOption(Map<String, Object> pair) {
        UserFile file = userFileService.getById(((Integer) pair.get("fileId")).longValue());
        return new AnswerOption(((Integer) pair.get("id")).longValue(), (String) pair.get("answer"), (Boolean) pair.get("isRight"), file);
    }

    private static List<WriteableAnswerOption> extractWriteableAnswerOptionList(ArrayList<Map<String, Object>> answers) {
        List<WriteableAnswerOption> fetchedAnswers = new ArrayList<>();
        for (Map<String, Object> pair : answers) {
            WriteableAnswerOption answerOption = new WriteableAnswerOption(((Integer) pair.get("id")).longValue(),
                    (String) pair.get("answer"), (Boolean) pair.get("isStrict"));
            fetchedAnswers.add(answerOption);
        }
        return fetchedAnswers;
    }

    private static List<MatchableAnswerOption> extractMatchableAnswerOptionList(ArrayList<Map<String, Object>> answers) {
        List<MatchableAnswerOption> fetchedAnswers = new ArrayList<>();
        for (Map<String, Object> pair : answers) {
            Map<String, Object> keyMap = (Map<String, Object>) pair.get("key");
            AnswerOption keyAnswerOption = extractAnswerOption(keyMap);

            Map<String, Object> valueMap = (Map<String, Object>) pair.get("value");
            AnswerOption valueAnswerOption = extractAnswerOption(valueMap);

            MatchableAnswerOption answerOption = new MatchableAnswerOption(((Integer) pair.get("id")).longValue(), keyAnswerOption, valueAnswerOption);
            fetchedAnswers.add(answerOption);
        }
        return fetchedAnswers;
    }
}
