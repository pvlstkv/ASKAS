package com.example.javaserver.testing.n.service;

import com.example.javaserver.testing.model.Theme;
import com.example.javaserver.testing.model.dto.nv.AnswerDTOOut;
import com.example.javaserver.testing.n.config.FieldCopyUtil;
import com.example.javaserver.testing.n.config.QuestionType;
import com.example.javaserver.testing.n.dto.mapper.CustomQuestionMapper;
import com.example.javaserver.testing.n.dto.question.QuestionDataDto;
import com.example.javaserver.testing.n.model.MatchableQuestion;
import com.example.javaserver.testing.n.model.QuestionData;
import com.example.javaserver.testing.n.model.SelectableQuestion;
import com.example.javaserver.testing.n.model.answer.AnswerOption;
import com.example.javaserver.testing.n.model.answer.MatchableAnswerOption;
import com.example.javaserver.testing.n.repo.QuestionRepo;
import com.example.javaserver.testing.n.service.model.ResultOfSomethingChecking;
import com.example.javaserver.testing.repo.ThemeRepo;
import com.example.javaserver.user.repo.UserRepo;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class TestService {
    private final CustomQuestionMapper customQuestionMapper;
    private final UserRepo userRepo;
    private final ThemeRepo themeRepo;
    private final QuestionRepo questionRepo;
    private final String doesntExistById = " с id %d в базе данных не существует. " +
            "Пожалуйста проверьте корретность введенных данных.";

    public TestService(UserRepo userRepo, ThemeRepo themeRepo, QuestionRepo questionRepo, CustomQuestionMapper customQuestionMapper) {
        this.userRepo = userRepo;
        this.themeRepo = themeRepo;
        this.questionRepo = questionRepo;
        this.customQuestionMapper = customQuestionMapper;
    }

    public Set<QuestionDataDto> createTest(Long themeId, Integer countOfQuestions) {
        ResultOfSomethingChecking checkResult = new ResultOfSomethingChecking();
        checkResult = checkResult.checkIfExistsInDB(new Theme(themeId), themeRepo, checkResult);
        if (!checkResult.getItsOK())
            throw checkResult.getResponseStatusException();
        if (countOfQuestions == null) {
            countOfQuestions = checkResult.getTheme().getQuestionQuantityInTest();
            if (countOfQuestions == null)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Уточните количество вопросов в запросе или в БД.");
        }
        if (countOfQuestions < 1)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Количество вопросов не может быть меньше 1.");
        Set<QuestionData> questions4Test = questionRepo.findAllByThemeId(themeId);
        countOfQuestions = Math.min(countOfQuestions, questions4Test.size());
        questions4Test = ImmutableSet.copyOf(Iterables.limit(questions4Test, countOfQuestions));
        Set<QuestionDataDto> question4TestDtos = new HashSet<>();
        for (QuestionData question : questions4Test) {
            if (question.getQuestionType().equals(QuestionType.MATCH)) {
                shuffleMatchableQuestion((MatchableQuestion) question);
                question4TestDtos.add(customQuestionMapper.toDto(question));
            } else if (question.getQuestionType().equals(QuestionType.SELECT) || question.getQuestionType().equals(QuestionType.SEQUENCE)) {
                shuffleSelectableQuestion((SelectableQuestion) question);
                question4TestDtos.add(customQuestionMapper.toDto(question));
            } else { // if type is WRITE
                question4TestDtos.add(customQuestionMapper.toDto(question));
            }
        }
        return question4TestDtos;
    }

    private void shuffleMatchableQuestion(MatchableQuestion matchableQuestion) {
        List<AnswerOption> keys = new ArrayList<>();
        List<AnswerOption> values = new ArrayList<>();
        for (MatchableAnswerOption answer : matchableQuestion.getMatchableAnswerOptionList()) {
            keys.add(answer.getKey());
            values.add(answer.getValue());
        }
        Collections.shuffle(keys);
        Collections.shuffle(values);
        List<MatchableAnswerOption> newMatchableAnswers = new ArrayList<>();
        for (int i = 0; i < keys.size(); i++) {
            newMatchableAnswers.add(new MatchableAnswerOption(keys.get(i), values.get(i)));
        }
        matchableQuestion.setMatchableAnswerOptionList(newMatchableAnswers);
    }

    private void shuffleSelectableQuestion(SelectableQuestion selectableQuestion) {
        Collections.shuffle(selectableQuestion.getAnswerOptionList());
    }

/*    public checkTest() {

    }*/

}
