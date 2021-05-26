package com.example.javaserver.testing.old.service;

import com.example.javaserver.general.model.UserDetailsImp;
import com.example.javaserver.testing.old.config.QuestionType;
import com.example.javaserver.testing.old.model.AnswerChoice;
import com.example.javaserver.testing.old.model.Question;
import com.example.javaserver.testing.old.model.dto.AnswerInOut;
import com.example.javaserver.testing.old.model.dto.QuestionOut;
import com.example.javaserver.testing.old.model.saving_result.PassedQuestion;
import com.example.javaserver.testing.old.model.saving_result.PassedTest;
import com.example.javaserver.testing.old.model.saving_result.UserAnswer;
import com.example.javaserver.testing.old.repo.PassedTestRepo;
import com.example.javaserver.testing.old.repo.QuestionRepo;
import com.example.javaserver.testing.old.service.model.ResultOfSomethingChecking;
import com.example.javaserver.testing.theme.Theme;
import com.example.javaserver.testing.theme.ThemeRepo;
import com.example.javaserver.user.model.User;
import com.example.javaserver.user.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    public List<QuestionOut> createTest(Long themeId, Integer countOfQuestions) {
        ResultOfSomethingChecking checkResult = new ResultOfSomethingChecking();
        checkResult = checkResult.checkIfExistsInDB(new Theme(themeId), themeRepo, checkResult);
        if (countOfQuestions == null) {
            countOfQuestions = checkResult.getTheme().getQuestionQuantityInTest();
            if (countOfQuestions == null)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Уточните количество вопросов в запросе или в БД.");
        }
        if (countOfQuestions < 1)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Количество вопросов не может быть меньше 1.");
        List<Question> questions4Test = questionRepo.findAllByThemeId(checkResult.getTheme().getId());
        Collections.shuffle(questions4Test);
        countOfQuestions = Math.min(countOfQuestions, questions4Test.size());
        questions4Test = questions4Test.subList(0, countOfQuestions);

        return prepareQuestionForTest(questions4Test);
    }

    private List<QuestionOut> prepareQuestionForTest(List<Question> questions) {
        List<QuestionOut> questionsOut = new ArrayList<>();
        for (Question originalQuestion : questions) {
            if (originalQuestion.getQuestionType().equals(QuestionType.CHOOSE) || originalQuestion.getQuestionType().equals(QuestionType.SEQUENCE)) {
                Collections.shuffle(originalQuestion.getAnswerChoiceList());
            } else if (originalQuestion.getQuestionType().equals(QuestionType.MATCH)) {
                shuffleMatchAnswers(originalQuestion);
            }
            questionsOut.add(new QuestionOut(originalQuestion));
        }
        return questionsOut;
    }

    private void shuffleMatchAnswers(Question originalQuestion) {
        List<AnswerChoice> keys = new ArrayList<>();
        List<AnswerChoice> values = new ArrayList<>();
        for (int i = 0; i < originalQuestion.getAnswerChoiceList().size(); i += 2) {
            keys.add(originalQuestion.getAnswerChoiceList().get(i));
            values.add(originalQuestion.getAnswerChoiceList().get(i + 1));
        }
        Collections.shuffle(keys);
        Collections.shuffle(values);
        List<AnswerChoice> shuffledAnswers = new ArrayList<>();
        for (int i = 0; i < keys.size(); i++) {
            shuffledAnswers.add(keys.get(i));
            shuffledAnswers.add(values.get(i));
        }
        originalQuestion.setAnswerChoiceList(shuffledAnswers);
    }

    public PassedTest checkTest(List<AnswerInOut> incomingQuestionsWithUserAnswer, UserDetailsImp userDetails) {
        PassedTest passedTest = new PassedTest();
        List<PassedQuestion> passedQuestions = new ArrayList<>();
        List<UserAnswer> userAnswers = new ArrayList<>();
        double totalRating = 0.0;
        for (AnswerInOut oneAnswer : incomingQuestionsWithUserAnswer) {
            userAnswers.clear();
            ResultOfSomethingChecking checkResult = new ResultOfSomethingChecking();
            checkResult = checkResult.checkIfExistsInDB(new Question(oneAnswer.getQuestionId()), questionRepo, checkResult);
            PassedQuestion currentPassedQuestion = new PassedQuestion();
            Question currentCheckingQuestion = checkResult.getQuestion();
            currentPassedQuestion.setQuestion(currentCheckingQuestion);
            currentPassedQuestion.setPassedTest(passedTest);
            switch (currentCheckingQuestion.getQuestionType()) {
                case MATCH:
                    totalRating += checkMatchTypeQuestion(currentCheckingQuestion,
                            oneAnswer.getAnswers(), currentPassedQuestion, userAnswers);
                    break;
                case WRITE: // write and choose are checking by one method
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
        saveTest(userDetails, passedTest, passedQuestions, resInPercent);
        return passedTest;
    }

    private void addAPassedQuestion(PassedTest passedTest, List<PassedQuestion> passedQuestions,
                                    PassedQuestion currentPassedQuestion, List<UserAnswer> userAnswers) {
        passedTest.setTheme(currentPassedQuestion.getQuestion().getTheme());
        currentPassedQuestion.setUserAnswers(userAnswers);
        currentPassedQuestion.setPassedTest(passedTest);
        passedQuestions.add(currentPassedQuestion);
    }

    private void saveTest(UserDetailsImp userDetails, PassedTest passedTest,
                          List<PassedQuestion> passedQuestions, int resInPercent) {
        passedTest.setPassedQuestions(passedQuestions);
        passedTest.setUser(fetchUser(userDetails));
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
        for (String userAnswer : listOfUserAnswers) {
            boolean userAnswerIsRight = false;
            for (String rightAnswer : listOfRightAnswers) {
                if (rightAnswer.equalsIgnoreCase(userAnswer.trim())) {
                    // if user gave right answers more than in the original question, then the answer is making true and adding points
                    if (listOfUserAnswers.size() <= listOfRightAnswers.size()) {
                        rightAnswersCounter += 1.0 / listOfRightAnswers.size();
                        userAnswerIsRight = true;
                    }
                    break;
                }
            }
            userAnswers4Saving.add(new UserAnswer(userAnswer, userAnswerIsRight, passedQuestion));
        }
        return rightAnswersCounter;
    }

    private double checkMatchTypeQuestion(Question checkingQuestion,
                                          List<String> listOfUserAnswers,
                                          PassedQuestion passedQuestion,
                                          List<UserAnswer> userAnswers4Saving) {
        double rightAnswersCounter = 0;
        List<String> originalAnswers = checkingQuestion.getAnswerChoiceList().stream().map(AnswerChoice::getAnswer).collect(Collectors.toList());
        boolean userAnswerIsRight;
        for (int i = 0; i < originalAnswers.size(); i += 2) {
            for (int j = 0; j < listOfUserAnswers.size(); j += 2) {
                if (originalAnswers.get(i).equals(listOfUserAnswers.get(j))) {
                    if (originalAnswers.get(i + 1).equals(listOfUserAnswers.get(j + 1))) {
                        userAnswerIsRight = true;
                        rightAnswersCounter += 1 / (originalAnswers.size() / 2.0);
                    } else {
                        userAnswerIsRight = false;
                    }
                    userAnswers4Saving.add(new UserAnswer(listOfUserAnswers.get(j), userAnswerIsRight, passedQuestion));
                    userAnswers4Saving.add(new UserAnswer(listOfUserAnswers.get(j + 1), userAnswerIsRight, passedQuestion));
                    break;
                }
            }
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
            for (String userAnswer : listOfUserAnswers) {
                userAnswers4Saving.add(new UserAnswer(userAnswer, false, passedQuestion));
            }
            return 0.0;
        }
        for (int i = 0; i < sizeRS; i++) {
            if (rightSequence.get(i).equals(listOfUserAnswers.get(i))) {
                rightAnswersCounter += 1.0 / sizeRS;
                userAnswers4Saving.add(new UserAnswer(listOfUserAnswers.get(i), true, passedQuestion));
            } else {
                rightAnswersCounter = 0;
                userAnswers4Saving.forEach(item -> item.setRight(false));
                break;
            }
        }
        return rightAnswersCounter;
    }

    private User fetchUser(UserDetailsImp userDetails) {
        Optional<User> user = userRepo.findById(userDetails.getId());
        return user.orElse(null);
    }
}
