package com.example.javaserver.testing.service;

import com.example.javaserver.general.model.Message;
import com.example.javaserver.general.model.UserContext;
import com.example.javaserver.testing.model.Theme;
import com.example.javaserver.testing.model.saving_result.PassedTest;
import com.example.javaserver.testing.repo.PassedTestRepo;
import com.example.javaserver.testing.repo.ThemeRepo;
import com.example.javaserver.testing.service.model.ResultOfSomethingChecking;
import com.example.javaserver.user.model.User;
import com.example.javaserver.user.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResultService {
    private final PassedTestRepo passedTestRepo;
    private final UserRepo userRepo;
    private final ThemeRepo themeRepo;
    private final String doesntExistById = " с id %d в базе данных не существует. " +
            "Пожалуйста проверьте корретность введенных данных.";

    @Autowired
    public ResultService(PassedTestRepo passedTestRepo, UserRepo userRepo, ThemeRepo themeRepo) {
        this.passedTestRepo = passedTestRepo;
        this.userRepo = userRepo;
        this.themeRepo = themeRepo;
    }

    public ResponseEntity<?> fetchUserPassedTestsByTheme(UserContext userContext, Long themeId){
        Optional<Theme> theme = themeRepo.findById(themeId);
        List<PassedTest> passedTests = passedTestRepo.findAllByUserAndTheme(fetchUser(userContext), theme.get());
        return new ResponseEntity<>(passedTests, HttpStatus.OK);
    }

    public ResponseEntity<?> fetchUserPassedThemes(UserContext userContext, Long subjectId){
        List<Long> themeIds = themeRepo.fetchPassedThemeIdsByUserId(userContext.getUserId(), subjectId);
        List<Theme> themes = themeRepo.findAllById(themeIds);
        return new ResponseEntity<>(themes, HttpStatus.OK);
    }

    public ResponseEntity<?> formUserPassedTest(UserContext userContext) {
        ResultOfSomethingChecking checkResult;
        checkResult = ResultOfSomethingChecking.checkIfExistsInDB(new User(userContext.getUserId()), userRepo);
        User user = fetchUser(userContext);
        if (user == null) {
            String response = String.format("Пользователя" + doesntExistById, userContext.getUserId());
            return new ResponseEntity<>(new Message(response), HttpStatus.BAD_REQUEST);
        }
        List<PassedTest> passedTests = passedTestRepo.findAllByUser(user);
        System.out.println(themeRepo.fetchPassedThemeIdsByUserId(user.getId(), 1L));
        return new ResponseEntity<>(passedTests, HttpStatus.OK);
    }

    private User fetchUser(UserContext userContext) {
        Optional<User> user = userRepo.findById(userContext.getUserId());
        return user.orElse(null);
    }
}
