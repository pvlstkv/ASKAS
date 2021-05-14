package com.example.javaserver.testing.old.service;

import com.example.javaserver.common_data.model.Subject;
import com.example.javaserver.common_data.repo.SubjectRepo;
import com.example.javaserver.general.model.UserDetailsImp;
import com.example.javaserver.testing.old.model.dto.PassedTestOut;
import com.example.javaserver.testing.old.model.dto.PassedThemeOut;
import com.example.javaserver.testing.old.model.saving_result.PassedTest;
import com.example.javaserver.testing.old.repo.PassedTestRepo;
import com.example.javaserver.testing.old.service.model.ResultOfSomethingChecking;
import com.example.javaserver.testing.theme.Theme;
import com.example.javaserver.testing.theme.ThemeRepo;
import com.example.javaserver.user.model.User;
import com.example.javaserver.user.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ResultService {
    private final PassedTestRepo passedTestRepo;
    private final UserRepo userRepo;
    private final ThemeRepo themeRepo;
    private final SubjectRepo subjectRepo;
    private final String doesntExistById = " с id %d в базе данных не существует. " +
            "Пожалуйста проверьте корретность введенных данных.";

    @Autowired
    public ResultService(PassedTestRepo passedTestRepo, UserRepo userRepo, ThemeRepo themeRepo, SubjectRepo subjectRepo) {
        this.passedTestRepo = passedTestRepo;
        this.userRepo = userRepo;
        this.themeRepo = themeRepo;
        this.subjectRepo = subjectRepo;
    }


    public List<PassedTest> fetchUserPassedTestsByThemeAndUserId(Integer userId, Long themeId, UserDetailsImp userDetailsImp) {
        ResultOfSomethingChecking checkResult = new ResultOfSomethingChecking();
        checkResult = checkResult.checkIfExistsInDB(new User(userId), userRepo, checkResult);
        checkResult = checkResult.checkIfExistsInDB(new Theme(themeId), themeRepo, checkResult);
        if (!checkResult.getItsOK()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, checkResult.getErrors());
        }
        return passedTestRepo.findAllByUserAndTheme(checkResult.getUser(), checkResult.getTheme());
    }

    public List<PassedThemeOut> fetchUserPassedThemesBySubjectIdAndUserId(Integer userId, Long subjectId, UserDetailsImp userDetails) {
        // todo set up the access control
        ResultOfSomethingChecking checkResult = new ResultOfSomethingChecking();
        checkResult = checkResult.checkIfExistsInDB(new User(userId), userRepo, checkResult);
        checkResult = checkResult.checkIfExistsInDB(new Subject(subjectId), subjectRepo, checkResult);
        List<Long> themeIds = themeRepo.fetchPassedThemeIdsByUserId(userId, subjectId);
        List<Theme> themes = themeRepo.findAllById(themeIds);
        User user = userRepo.findById(userId).get();
        List<PassedThemeOut> passedThemeOuts = new ArrayList<>();
        for (Theme theme : themes) {
            List<PassedTest> passedTests = passedTestRepo.findAllByUserAndTheme(user, theme);
            List<Integer> ratings = new ArrayList<>();
            passedTests.forEach(item -> ratings.add(item.getRatingInPercent()));
            passedThemeOuts.add(new PassedThemeOut(theme, ratings));
        }
        return passedThemeOuts;
    }

//    public List<PassedTestOut> fetchUserPassedThemesBySubjectIdAndUserIdReduced(Integer userId, Long subjectId) {
//        ResultOfSomethingChecking checkResult = new ResultOfSomethingChecking();
//        checkResult = checkResult.checkIfExistsInDB(new User(userId), userRepo, checkResult);
//        checkResult = checkResult.checkIfExistsInDB(new Subject(subjectId), subjectRepo, checkResult);
//        if (!checkResult.getItsOK()) {
//            throw checkResult.getResponseStatusException();
//        }
//        List<PassedTestOut> results = new ArrayList<>();
//        List<Long> themeIds = themeRepo.fetchPassedThemeIdsByUserId(userId, subjectId);
//        List<Theme> themes = themeRepo.findAllById(themeIds);
//
//
//    }


    public List<PassedTestOut> fetchPassedThemesUsersByGroupId(Long groupId, Long themeId) {
        List<User> users = userRepo.findAllByStudyGroupId(groupId);
        List<PassedTestOut> results = new ArrayList<>();
        Optional<Theme> themeOptional = themeRepo.findById(themeId);
        if (!themeOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Неверный id темы.");
        }
        Theme theme = themeOptional.get();
        for (User user : users) {
            List<PassedTest> userPassedTest = passedTestRepo.findAllByUserAndTheme(user, theme);
            List<Integer> ratingsPerUser = new ArrayList<>();
            userPassedTest.forEach(item -> ratingsPerUser.add(item.getRatingInPercent()));
            results.add(new PassedTestOut(user.getId(), user.getFirstName(), user.getLastName(), user.getPatronymic(),
                    themeId, theme.getName(), ratingsPerUser));
        }
        return results;
    }

    public List<PassedTest> formUserPassedTest(UserDetailsImp userDetails) {
        ResultOfSomethingChecking checkResult = new ResultOfSomethingChecking();
        checkResult = checkResult.checkIfExistsInDB(new User(userDetails.getId()), userRepo, checkResult);
        User user = fetchUser(userDetails);
        if (user == null) {
            String response = String.format("Пользователя" + doesntExistById, userDetails.getId());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, response);
        }
        List<PassedTest> passedTests = passedTestRepo.findAllByUser(user);
        System.out.println(themeRepo.fetchPassedThemeIdsByUserId(user.getId(), 1L));
        return passedTests;
    }

//    public List<PassedTestOut> formPassedTestByUser(Integer userId){
//        List<PassedTestOut> results = new ArrayList<>();
//
//    }

    private User fetchUser(UserDetailsImp userDetails) {
        Optional<User> user = userRepo.findById(userDetails.getId());
        return user.orElse(null);
    }
}
