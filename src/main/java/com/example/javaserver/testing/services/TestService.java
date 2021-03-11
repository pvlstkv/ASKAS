package com.example.javaserver.testing.services;

import com.example.javaserver.common_data.model.Subject;
import com.example.javaserver.common_data.model.Theme;
import com.example.javaserver.general.model.Message;
import com.example.javaserver.general.model.UserContext;
import com.example.javaserver.testing.models.Question;
import com.example.javaserver.testing.models.dto.AnswerInOut;
import com.example.javaserver.testing.models.dto.QuestionOut;
import com.example.javaserver.testing.models.dto.ResultOut;
import com.example.javaserver.testing.models.saving_results.PassedQuestion;
import com.example.javaserver.testing.models.saving_results.PassedTest;
import com.example.javaserver.testing.models.saving_results.UserAnswer;
import com.example.javaserver.testing.repo.PassedTestRepo;
import com.example.javaserver.testing.repo.QuestionRepo;
import com.example.javaserver.testing.repo.ThemeRepo;
import com.example.javaserver.testing.services.models.CheckedQuestion;
import com.example.javaserver.testing.services.models.ResultOfSomethingChecking;
import com.example.javaserver.user.model.User;
import com.example.javaserver.user.repo.UserRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;
@Service
public class TestService {
    private final PassedTestRepo passedTestRepo;
    private final UserRepo userRepo;
    private final ThemeRepo themeRepo;
    private final QuestionRepo questionRepo;
    private final String doesntExistById = " с id %d в базе данных не существует. " +
            "Пожалуйста проверьте корретность введенных данных.";


    public TestService(PassedTestRepo passedTestRepo, UserRepo userRepo, ThemeRepo themeRepo, QuestionRepo questionRepo) {
        this.passedTestRepo = passedTestRepo;
        this.userRepo = userRepo;
        this.themeRepo = themeRepo;
        this.questionRepo = questionRepo;
    }

    public ResponseEntity<?> createTest(Long subjectId, Long themeId, int countOfQuestions) {
        if (countOfQuestions < 1)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        ResultOfSomethingChecking result = new ResultOfSomethingChecking();
        ResultOfSomethingChecking.checkIfExistsInDB(new Theme(themeId), themeRepo, result);
        if (!result.getItsOK()) return result.getResponseEntity();
        List<Question> questions4Test = questionRepo.findAllByTheme(result.getTheme());
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
        Question currentCheckingQuestion;
        Optional<Question> optionalQuestion;
        CheckedQuestion checkedQuestion;
//        List<AnswerInOut> answerInOutList = new ArrayList<>();

        PassedTest passedTest = new PassedTest();
//        Set<PassedQuestion> passedQuestions = new TreeSet<>();
        List<PassedQuestion> passedQuestions = new ArrayList<>();
//        passedTest.setPassedQuestions(passedQuestions);
        PassedQuestion currentPassedQuestion = new PassedQuestion();
        List<UserAnswer> userAnswers = new ArrayList<>();
        double totalRating = 0.0;
        for (AnswerInOut oneAnswer : incomingQuestionsWithUserAnswer) {
            userAnswers.clear();
            ResultOfSomethingChecking result = new ResultOfSomethingChecking();
            ResultOfSomethingChecking.checkIfExistsInDB(new Question(oneAnswer.getQuestionId()), questionRepo, result);
            if (!result.getItsOK()){
                return result.getResponseEntity();
            }
            currentPassedQuestion = new PassedQuestion();
            currentCheckingQuestion = result.getQuestion();
            currentPassedQuestion.setQuestion(currentCheckingQuestion);
            currentPassedQuestion.setPassedTest(passedTest);
            switch (currentCheckingQuestion.getQuestionType()) {
                case MATCH:
                    // todo there are some problems...
                    break;
                case WRITE:
                case CHOOSE:
                    checkedQuestion = checkChooseAndWriteTypeQuestion(currentCheckingQuestion,
                            oneAnswer.getAnswers(), currentPassedQuestion, userAnswers);
                    totalRating += checkedQuestion.getRating();
//                    answerInOutList.add(checkedQuestion.getAnswerInOut());
                    break;
                case SEQUENCE:
                    checkedQuestion = checkSequenceTypeQuestion(currentCheckingQuestion,
                            oneAnswer.getAnswers(), currentPassedQuestion, userAnswers);
                    totalRating += checkedQuestion.getRating();
//                    answerInOutList.add(checkedQuestion.getAnswerInOut());
                    break;
            }
            addAPassedQuestion(passedTest, passedQuestions, currentPassedQuestion, new ArrayList<>(userAnswers));
        }
        int resInPercent = (int) Math.round(totalRating * 100.0 / incomingQuestionsWithUserAnswer.size());
//        ResultOut res = new ResultOut(answerInOutList, resInPercent);
        saveTest(userContext, passedTest, passedQuestions, resInPercent);
        return new ResponseEntity<>(passedTest, HttpStatus.OK);
    }

    private void addAPassedQuestion(PassedTest passedTest, List<PassedQuestion> passedQuestions, PassedQuestion currentPassedQuestion, List<UserAnswer> userAnswers) {
        currentPassedQuestion.setUserAnswers(userAnswers);
        currentPassedQuestion.setPassedTest(passedTest);
        passedQuestions.add(currentPassedQuestion);
    }

    private void saveTest(UserContext userContext, PassedTest passedTest, List<PassedQuestion> passedQuestions, int resInPercent) {
        passedTest.setPassedQuestions(passedQuestions);
        passedTest.setUser(fetchUser(userContext));
        passedTest.setRating(resInPercent);
        passedTest.setPassedAt(OffsetDateTime.now());
        passedTestRepo.save(passedTest);
    }

    private CheckedQuestion checkChooseAndWriteTypeQuestion(Question checkingQuestion,
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
        AnswerInOut out = new AnswerInOut(checkingQuestion.getId(), listOfRightAnswers);
        return new CheckedQuestion(out, rightAnswersCounter);
    }

    private CheckedQuestion checkSequenceTypeQuestion(Question checkingQuestion,
                                                      List<String> listOfUserAnswers,
                                                      PassedQuestion passedQuestion,
                                                      List<UserAnswer> userAnswers4Saving) {
        double rightAnswersCounter = 0;
        List<String> rightSequence = checkingQuestion.fetchRightAnswers();
        int sizeRS = rightSequence.size();
        if (listOfUserAnswers.size() != sizeRS) {
            userAnswers4Saving = listOfUserAnswers.stream().map(item -> new UserAnswer(item, false, passedQuestion))
                    .collect(Collectors.toList());
            return new CheckedQuestion(new AnswerInOut(checkingQuestion.getId(), listOfUserAnswers), 0.0);
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
        return new CheckedQuestion(new AnswerInOut(checkingQuestion.getId(), rightSequence), rightAnswersCounter);
    }

    private User fetchUser(UserContext userContext) {
        Optional<User> user = userRepo.findById(userContext.getUserId());
        return user.orElse(null);
    }
}
