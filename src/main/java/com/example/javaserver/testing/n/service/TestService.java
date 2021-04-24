package com.example.javaserver.testing.n.service;

import com.example.javaserver.testing.model.Theme;
import com.example.javaserver.testing.n.model.QuestionData;
import com.example.javaserver.testing.n.repo.QuestionRepo;
import com.example.javaserver.testing.n.service.model.ResultOfSomethingChecking;
import com.example.javaserver.testing.repo.ThemeRepo;
import com.example.javaserver.user.repo.UserRepo;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

@Service
public class TestService {
    private final UserRepo userRepo;
    private final ThemeRepo themeRepo;
    private final QuestionRepo questionRepo;
    private final String doesntExistById = " с id %d в базе данных не существует. " +
            "Пожалуйста проверьте корретность введенных данных.";

    public TestService(UserRepo userRepo, ThemeRepo themeRepo, QuestionRepo questionRepo) {
        this.userRepo = userRepo;
        this.themeRepo = themeRepo;
        this.questionRepo = questionRepo;
    }

    public Set<QuestionData> createTest(Long themeId, Integer countOfQuestions) {
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
        return questions4Test;
    }

/*    public checkTest() {

    }*/

}
