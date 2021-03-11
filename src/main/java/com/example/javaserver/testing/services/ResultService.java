package com.example.javaserver.testing.services;

import com.example.javaserver.general.model.Message;
import com.example.javaserver.general.model.UserContext;
import com.example.javaserver.testing.models.dto.passed_test.TestOut;
import com.example.javaserver.testing.models.saving_results.PassedTest;
import com.example.javaserver.testing.repo.PassedTestRepo;
import com.example.javaserver.testing.repo.QuestionRepo;
import com.example.javaserver.testing.repo.ThemeRepo;
import com.example.javaserver.testing.services.models.ResultOfSomethingChecking;
import com.example.javaserver.user.model.User;
import com.example.javaserver.user.repo.UserRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResultService {
    private final PassedTestRepo passedTestRepo;
    private final UserRepo userRepo;
    private final String doesntExistById = " с id %d в базе данных не существует. " +
            "Пожалуйста проверьте корретность введенных данных.";

    public ResultService(PassedTestRepo passedTestRepo, UserRepo userRepo) {
        this.passedTestRepo = passedTestRepo;
        this.userRepo = userRepo;
    }

    public ResponseEntity<?> formUserPassedTest(UserContext userContext) {
        ResultOfSomethingChecking result = new ResultOfSomethingChecking();
        ResultOfSomethingChecking.checkIfExistsInDB(new User(userContext.getUserId()), userRepo, result);
        User user = fetchUser(userContext);
        if (user == null) {
            String response = String.format("Пользователя" + doesntExistById, userContext.getUserId());
            return new ResponseEntity<>(new Message(response), HttpStatus.BAD_REQUEST);
        }
        List<PassedTest> passedTests = passedTestRepo.findAllByUser(user);
        TestOut testOut = new TestOut();
        testOut.setPassedTests(passedTests);
        return new ResponseEntity<>(passedTests, HttpStatus.OK);
    }

    private User fetchUser(UserContext userContext) {
        Optional<User> user = userRepo.findById(userContext.getUserId());
        return user.orElse(null);
    }
}
