package com.example.javaserver.testing.service;

import com.example.javaserver.general.model.Message;
import com.example.javaserver.testing.model.Theme;
import com.example.javaserver.general.model.UserContext;
import com.example.javaserver.testing.model.Question;
import com.example.javaserver.testing.model.dto.AnswerInOut;
import com.example.javaserver.testing.model.dto.QuestionOut;
import com.example.javaserver.testing.model.saving_result.PassedQuestion;
import com.example.javaserver.testing.model.saving_result.PassedTest;
import com.example.javaserver.testing.model.saving_result.UserAnswer;
import com.example.javaserver.testing.repo.PassedTestRepo;
import com.example.javaserver.testing.repo.QuestionRepo;
import com.example.javaserver.testing.repo.ThemeRepo;
import com.example.javaserver.testing.service.model.ResultOfSomethingChecking;
import com.example.javaserver.user.model.User;
import com.example.javaserver.user.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TestService {
    private final PassedTestRepo passedTestRepo;
    private final UserRepo userRepo;
    private final ThemeRepo themeRepo;
    private final QuestionRepo questionRepo;
    private final String doesntExistById = " с id %d в базе данных не существует. " +
            "Пожалуйста проверьте корретность введенных данных.";

    @Autowired
    public TestService(PassedTestRepo passedTestRepo, UserRepo userRepo, ThemeRepo themeRepo, QuestionRepo questionRepo) {
        this.passedTestRepo = passedTestRepo;
        this.userRepo = userRepo;
        this.themeRepo = themeRepo;
        this.questionRepo = questionRepo;
    }

    public ResponseEntity<?> createTest(Long themeId, Integer countOfQuestions) {
        ResultOfSomethingChecking checkResult;
        checkResult = ResultOfSomethingChecking.checkIfExistsInDB(new Theme(themeId), themeRepo);
        if (!checkResult.getItsOK())
            return checkResult.getResponseEntity();
        if (countOfQuestions == null) {
            countOfQuestions = checkResult.getTheme().getQuestionQuantityInTest();
            if (countOfQuestions == null)
                return new ResponseEntity<>(new Message("Уточните количество вопросов в запросе или в БД."),
                        HttpStatus.BAD_REQUEST);
        }
        if (countOfQuestions < 1)
            return new ResponseEntity<>(new Message("Количество вопросов не может быть меньше 1."),
                    HttpStatus.BAD_REQUEST);

        List<Question> questions4Test = questionRepo.findAllByTheme(checkResult.getTheme());
        Collections.shuffle(questions4Test);
        countOfQuestions = Math.min(countOfQuestions, questions4Test.size());
        questions4Test = questions4Test.subList(0, countOfQuestions);
        List<QuestionOut> questionsOut = new ArrayList<>();
        for (Question originalQuestion : questions4Test) {
            questionsOut.add(new QuestionOut(originalQuestion));
        }
        return new ResponseEntity<>(questionsOut, HttpStatus.OK);
    }

    public ResponseEntity<?> checkTest(List<AnswerInOut> incomingQuestionsWithUserAnswer, UserContext userContext) {
        PassedTest passedTest = new PassedTest();
        List<PassedQuestion> passedQuestions = new ArrayList<>();
        List<UserAnswer> userAnswers = new ArrayList<>();
        double totalRating = 0.0;
        for (AnswerInOut oneAnswer : incomingQuestionsWithUserAnswer) {
            userAnswers.clear();
            ResultOfSomethingChecking checkResult;
            checkResult = ResultOfSomethingChecking.checkIfExistsInDB(new Question(oneAnswer.getQuestionId()), questionRepo);
            if (!checkResult.getItsOK()) {
                return checkResult.getResponseEntity();
            }
            PassedQuestion currentPassedQuestion = new PassedQuestion();
            Question currentCheckingQuestion = checkResult.getQuestion();
            currentPassedQuestion.setQuestion(currentCheckingQuestion);
            currentPassedQuestion.setPassedTest(passedTest);
            switch (currentCheckingQuestion.getQuestionType()) {
                case MATCH:
                    // todo there are some problems...
                    break;
                case WRITE:
                case CHOOSE:
                    totalRating += checkChooseAndWriteTypeQuestion(currentCheckingQuestion,
                            oneAnswer.getAnswers(), currentPassedQuestion, userAnswers);
                    break;
                case SEQUENCE:
                    totalRating += checkSequenceTypeQuestion(currentCheckingQuestion,
                            oneAnswer.getAnswers(), currentPassedQuestion, userAnswers);
                    break;
            }
            addAPassedQuestion(passedTest, passedQuestions, currentPassedQuestion, new ArrayList<>(userAnswers));
        }
        int resInPercent = (int) Math.round(totalRating * 100.0 / incomingQuestionsWithUserAnswer.size());
        saveTest(userContext, passedTest, passedQuestions, resInPercent);
        return new ResponseEntity<>(passedTest, HttpStatus.OK);
    }

    private void addAPassedQuestion(PassedTest passedTest, List<PassedQuestion> passedQuestions,
                                    PassedQuestion currentPassedQuestion, List<UserAnswer> userAnswers) {
        passedTest.setTheme(currentPassedQuestion.getQuestion().getTheme());
        currentPassedQuestion.setUserAnswers(userAnswers);
        currentPassedQuestion.setPassedTest(passedTest);
        passedQuestions.add(currentPassedQuestion);
    }

    private void saveTest(UserContext userContext, PassedTest passedTest,
                          List<PassedQuestion> passedQuestions, int resInPercent) {
        passedTest.setPassedQuestions(passedQuestions);
        passedTest.setUser(fetchUser(userContext));
        passedTest.setRatingInPercent(resInPercent);
        passedTest.setPassedAt(OffsetDateTime.now());
        passedTestRepo.save(passedTest);
    }

    private double checkChooseAndWriteTypeQuestion(Question checkingQuestion,
                                                   List<String> listOfUserAnswers,
                                                   PassedQuestion passedQuestion,
                                                   List<UserAnswer> userAnswers4Saving) {
        double rightAnswersCounter = 0;
        List<String> listOfRightAnswers = checkingQuestion.fetchRightAnswers();
        boolean userAnswerIsRight;
        for (String userAnswer : listOfUserAnswers) {
            userAnswerIsRight = false;
            for (String rightAnswer : listOfRightAnswers) {
                if (rightAnswer.equals(userAnswer)) {
                    userAnswerIsRight = true;
                    // if user gave right answers more than in the original question, then the answer is making true and adding points
                    if (listOfUserAnswers.size() <= listOfRightAnswers.size()) {
                        rightAnswersCounter += 1.0 / listOfRightAnswers.size();
                    }
                    break;
                }
            }
            userAnswers4Saving.add(new UserAnswer(userAnswer, userAnswerIsRight, passedQuestion));
        }
        return rightAnswersCounter;
    }

    private double checkSequenceTypeQuestion(Question checkingQuestion,
                                             List<String> listOfUserAnswers,
                                             PassedQuestion passedQuestion,
                                             List<UserAnswer> userAnswers4Saving) {
        double rightAnswersCounter = 0;
        List<String> rightSequence = checkingQuestion.fetchRightAnswers();
        int sizeRS = rightSequence.size();
        if (listOfUserAnswers.size() != sizeRS) {
            userAnswers4Saving = listOfUserAnswers.stream().map(item -> new UserAnswer(item, false, passedQuestion))
                    .collect(Collectors.toList());
            return 0.0;
        }
        for (int i = 0; i < sizeRS; i++) {
            if (rightSequence.get(i).equals(listOfUserAnswers.get(i))) {
                rightAnswersCounter += 1.0 / sizeRS;
                userAnswers4Saving.add(new UserAnswer(listOfUserAnswers.get(i), true, passedQuestion));
            } else {
                userAnswers4Saving.forEach(item -> item.setRight(false));
                break;
            }
        }
        return rightAnswersCounter;
    }

    private User fetchUser(UserContext userContext) {
        Optional<User> user = userRepo.findById(userContext.getUserId());
        return user.orElse(null);
    }
}
