package com.example.javaserver.testing.new_v.service;

import com.example.javaserver.common_data.model.Subject;
import com.example.javaserver.common_data.repo.SubjectRepo;
import com.example.javaserver.general.model.UserDetailsImp;


import com.example.javaserver.testing.new_v.dto.answer.for_test.result.AfterCheckTestDto;
import com.example.javaserver.testing.new_v.dto.mapper.ResultMapper;
import com.example.javaserver.testing.new_v.model.saving_result.PassedTestN;
import com.example.javaserver.testing.new_v.repo.PassedTestRepoN;
import com.example.javaserver.testing.new_v.service.model.ResultOfSomethingChecking;
import com.example.javaserver.testing.new_v.dto.test.PassedTestPerUserDto;
import com.example.javaserver.testing.theme.dto.theme.PassedThemeDto;
import com.example.javaserver.testing.theme.Theme;
import com.example.javaserver.testing.theme.ThemeRepo;
import com.example.javaserver.user.model.User;
import com.example.javaserver.user.repo.UserRepo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ResultServiceN {
    private final ResultMapper resultMapper;
    private final PassedTestRepoN passedTestRepoN;
    private final UserRepo userRepo;
    private final ThemeRepo themeRepo;
    private final SubjectRepo subjectRepo;
    private final String doesntExistById = " с id %d в базе данных не существует. " +
            "Пожалуйста проверьте корретность введенных данных.";

    public ResultServiceN(ResultMapper resultMapper, PassedTestRepoN passedTestRepoN, UserRepo userRepo, ThemeRepo themeRepo, SubjectRepo subjectRepo) {
        this.resultMapper = resultMapper;
        this.passedTestRepoN = passedTestRepoN;
        this.userRepo = userRepo;
        this.themeRepo = themeRepo;
        this.subjectRepo = subjectRepo;
    }

    public List<AfterCheckTestDto> fetchUserPassedTestsByThemeAndUserId(Integer userId, Long themeId, UserDetailsImp userDetailsImp) {
        ResultOfSomethingChecking checkResult = new ResultOfSomethingChecking();
        checkResult = checkResult.checkIfExistsInDB(new User(userId), userRepo, checkResult);
        checkResult = checkResult.checkIfExistsInDB(new Theme(themeId), themeRepo, checkResult);
        if (!checkResult.getItsOK()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, checkResult.getErrors());
        }
        List<PassedTestN> passedTestNS = passedTestRepoN.findAllByUserAndTheme(checkResult.getUser(), checkResult.getTheme());
        return resultMapper.toDto(passedTestNS);
    }

    public List<PassedThemeDto> fetchUserPassedThemesBySubjectIdAndUserId(Integer userId, Long subjectId, UserDetailsImp userDetails) {
        // todo set up the access control
        ResultOfSomethingChecking checkResult = new ResultOfSomethingChecking();
        checkResult = checkResult.checkIfExistsInDB(new User(userId), userRepo, checkResult);
        checkResult = checkResult.checkIfExistsInDB(new Subject(subjectId), subjectRepo, checkResult);
        List<Long> themeIds = themeRepo.fetchPassedThemeIdsByUserIdN(userId, subjectId);
        List<Theme> themes = themeRepo.findAllById(themeIds);
        User user = userRepo.findById(userId).get();
        List<PassedThemeDto> passedThemeDtos = new ArrayList<>();
        for (Theme theme : themes) {
            List<PassedTestN> passedTestNS = passedTestRepoN.findAllByUserAndTheme(user, theme);
            List<Integer> ratings = new ArrayList<>();
            passedTestNS.forEach(item -> ratings.add(item.getRatingInPercent()));
            passedThemeDtos.add(new PassedThemeDto(theme, ratings));
        }
        return passedThemeDtos;
    }

    public List<PassedThemeDto> getPassedThemesByUserIdAndSubjectId(Integer userId, Long subjectId) {
        // todo set up the access control
        ResultOfSomethingChecking checkResult = new ResultOfSomethingChecking();
        checkResult = checkResult.checkIfExistsInDB(new User(userId), userRepo, checkResult);
        checkResult = checkResult.checkIfExistsInDB(new Subject(subjectId), subjectRepo, checkResult);
        List<Long> themeIds = themeRepo.fetchPassedThemeIdsByUserIdN(userId, subjectId);
        List<Theme> themes = themeRepo.findAllById(themeIds);
        User user = userRepo.findById(userId).get();
        List<PassedThemeDto> passedThemeDtos = new ArrayList<>();
        for (Theme theme : themes) {
            List<PassedTestN> passedTestNS = passedTestRepoN.findAllByUserAndTheme(user, theme);
            List<Integer> ratings = new ArrayList<>();
            passedTestNS.forEach(item -> ratings.add(item.getRatingInPercent()));
            passedThemeDtos.add(new PassedThemeDto(theme, ratings));
        }
        return passedThemeDtos;
    }


    public List<PassedTestPerUserDto> fetchPassedThemesUsersByGroupId(Long groupId, Long themeId) {
        List<User> users = userRepo.findAllByStudyGroupId(groupId);
        List<PassedTestPerUserDto> results = new ArrayList<>();
        Optional<Theme> themeOptional = themeRepo.findById(themeId);
        if (!themeOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Неверный id темы.");
        }
        Theme theme = themeOptional.get();
        for (User user : users) {
            List<PassedTestN> userPassedTestN = passedTestRepoN.findAllByUserAndTheme(user, theme);
            List<Integer> ratingsPerUser = new ArrayList<>();
            userPassedTestN.forEach(item -> ratingsPerUser.add(item.getRatingInPercent()));
            results.add(new PassedTestPerUserDto(user.getId(), user.getFirstName(), user.getLastName(), user.getPatronymic(),
                    themeId, theme.getName(), ratingsPerUser));
        }
        return results;
    }

    public List<AfterCheckTestDto> formUserPassedTest(UserDetailsImp userDetails) {
        ResultOfSomethingChecking checkResult = new ResultOfSomethingChecking();
        checkResult = checkResult.checkIfExistsInDB(new User(userDetails.getId()), userRepo, checkResult);
        User user = fetchUser(userDetails);
        if (user == null) {
            String response = String.format("Пользователя" + doesntExistById, userDetails.getId());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, response);
        }
        List<PassedTestN> passedTestNS = passedTestRepoN.findAllByUser(user);
        return resultMapper.toDto(passedTestNS);
    }

    private User fetchUser(UserDetailsImp userDetails) {
        Optional<User> user = userRepo.findById(userDetails.getId());
        return user.orElse(null);
    }
}
