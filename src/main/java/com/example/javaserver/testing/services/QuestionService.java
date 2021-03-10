package com.example.javaserver.testing.services;

import com.example.javaserver.common_data.model.Subject;
import com.example.javaserver.common_data.model.Theme;
import com.example.javaserver.common_data.repo.SubjectRepo;
import com.example.javaserver.general.model.Message;
import com.example.javaserver.general.model.UserContext;
import com.example.javaserver.general.service.RequestHandlerService;
import com.example.javaserver.testService.new_version.models.InOutComingModels.*;
import com.example.javaserver.testing.models.InOutComingModels.*;
import com.example.javaserver.testing.models.InOutComingModels.passed_test.TestOut;
import com.example.javaserver.testing.models.Question;
import com.example.javaserver.testing.models.saving_results.PassedQuestion;
import com.example.javaserver.testing.models.saving_results.PassedTest;
import com.example.javaserver.testing.models.saving_results.UserAnswer;
import com.example.javaserver.testing.repo.PassedTestRepo;
import com.example.javaserver.testing.repo.QuestionRepo;
import com.example.javaserver.testing.repo.ThemeRepo;
import com.example.javaserver.testing.services.models.CheckedQuestion;
import com.example.javaserver.user.model.User;
import com.example.javaserver.user.model.UserRole;
import com.example.javaserver.user.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    @Autowired
    private PassedTestRepo passedTestRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private QuestionRepo questionRepo;

    @Autowired
    private SubjectRepo subjectRepo;

    @Autowired
    private ThemeRepo themeRepo;

    @Autowired
    private RequestHandlerService requestHandlerService;

    private final String doesntExistById = " с id %d в базе данных не существует. " +
            "Пожалуйста проверьте корретность введенных данных.";

    private final String questionDoesntExist = "Вопрос с ";

    public ResponseEntity<?> createQuestions(TestIn testIn, String token) {
        return requestHandlerService.proceed(token, userContext -> addQuestions2TheDB(testIn),
                EnumSet.of(UserRole.ADMIN, UserRole.TEACHER));
    }

    private ResponseEntity<?> addQuestions2TheDB(TestIn testIn) {
        Question newQuestion;
        Long l = testIn.getSubjectId();
        Subject tempSubject = (subjectRepo.findById(l)
                .orElse(null));
        if (tempSubject == null) {
            String response = String.format("Предмета" + doesntExistById, testIn.getSubjectId());
            return new ResponseEntity<>(new Message(response), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        Optional<Theme> theme = themeRepo.findById(testIn.getThemeId());
        if (!theme.isPresent()) {
            String response = String.format("Темы" + doesntExistById, testIn.getThemeId());
            return new ResponseEntity<>(new Message(response), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        for (QuestionIn oneRawQuestion : testIn.getQuestionIns()) {
            // можно добавить проверку на существовние добаляемого вопроса
            newQuestion = new Question(oneRawQuestion, tempSubject, theme.get());
            Question finalNewQuestion = newQuestion;
            newQuestion.getAnswerChoiceList().forEach(answerChoice -> answerChoice.setQuestion(finalNewQuestion));
            questionRepo.save(newQuestion);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public ResponseEntity<?> updateQuestions(TestIn testIn, String token) {
        return requestHandlerService.proceed(token, userContext -> refreshQuestionsInTheDB(testIn),
                EnumSet.of(UserRole.ADMIN, UserRole.TEACHER));
    }

    private ResponseEntity<?> refreshQuestionsInTheDB(TestIn testIn) {
        Optional<Question> oldQuestion;
        Question newQuestion;
        Subject tempSubject = (subjectRepo.findById(testIn.getSubjectId()))
                .orElse(null);
        if (tempSubject == null) {
            String response = String.format("Предмета" + doesntExistById, testIn.getSubjectId());
            return new ResponseEntity<>(new Message(response), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        Optional<Theme> theme = themeRepo.findById(testIn.getThemeId());
        if (!theme.isPresent()) {
            String response = String.format("Темы" + doesntExistById, testIn.getThemeId());
            return new ResponseEntity<>(new Message(response), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        for (QuestionIn oneRawQuestion : testIn.getQuestionIns()) {
            oldQuestion = questionRepo.findById(oneRawQuestion.getId());
            if (!oldQuestion.isPresent()) {
                String response = String.format("Вопроса" + doesntExistById, testIn.getSubjectId());
                return new ResponseEntity<>(new Message(response), HttpStatus.UNPROCESSABLE_ENTITY);
            }
            newQuestion = new Question(oneRawQuestion, tempSubject, theme.get());
            Question finalNewQuestion = newQuestion;
            newQuestion.getAnswerChoiceList().stream().forEach(answerChoice -> answerChoice.setQuestion(finalNewQuestion));
            questionRepo.save(newQuestion);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<?> deleteManyQuestions(List<Long> ids, String token) {
        return requestHandlerService.proceed(token, userContext -> removeQuestionsInTheDB(ids),
                EnumSet.of(UserRole.ADMIN, UserRole.TEACHER));
    }

    private ResponseEntity<?> removeQuestionsInTheDB(List<Long> ids) {
        for (Long id : ids) {
            questionRepo.deleteById(id);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<?> deleteAllQuestions(String token) {
        return requestHandlerService.proceed(token,
                userContext -> {
                    questionRepo.deleteAll();
                    return new ResponseEntity<>(HttpStatus.OK);
                },
                EnumSet.of(UserRole.ADMIN, UserRole.TEACHER));
    }

    public ResponseEntity<?> createTest(Long subjectId, Long themeId, int countOfQuestions, String token) {
        return requestHandlerService.proceed(token, userContext -> makeTestFromDBQuestions(subjectId, themeId, countOfQuestions),
                EnumSet.of(UserRole.ADMIN, UserRole.TEACHER, UserRole.USER));
    }

    private ResponseEntity<?> makeTestFromDBQuestions(Long subjectId, Long themeId, int countOfQuestions) {

        if (countOfQuestions < 1)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        // it's not necessary because every theme is unique
//        Subject tempSubject = (subjectRepo.findById(subjectId))
//                .orElse(null);
//        if (tempSubject == null) {
//            String response = String.format("Предмета" + doesntExistById, subjectId);
//            return new ResponseEntity<>(new Message(response), HttpStatus.UNPROCESSABLE_ENTITY);
//        }
        Optional<Theme> theme = themeRepo.findById(themeId);
        if (!theme.isPresent()) {
            String response = String.format("Темы" + doesntExistById, themeId);
            return new ResponseEntity<>(new Message(response), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        List<Question> questions4Test = questionRepo.findAllByTheme(theme.get());
        Collections.shuffle(questions4Test);
        countOfQuestions = Math.min(countOfQuestions, questions4Test.size());
        questions4Test = questions4Test.subList(0, countOfQuestions);
        List<QuestionOut> questionsOut = new ArrayList<>();
        for (Question originalQuestion : questions4Test) {
            questionsOut.add(new QuestionOut(originalQuestion));
        }
        return new ResponseEntity<>(questionsOut, HttpStatus.OK);
    }

    public ResponseEntity<?> checkTest(List<AnswerInOut> userTest, String token) {
        return requestHandlerService.proceed(token, userContext -> checkingAnswers(userTest, userContext),
                EnumSet.of(UserRole.ADMIN, UserRole.TEACHER, UserRole.USER));
    }

    private ResponseEntity<ResultOut> checkingAnswers(List<AnswerInOut> incomingQuestionsWithUserAnswer, UserContext userContext) {
        Question currentCheckingQuestion;
        Optional<Question> optionalQuestion;
        CheckedQuestion checkedQuestion;
        List<AnswerInOut> answerInOutList = new ArrayList<>();

        PassedTest passedTest = new PassedTest();
        Set<PassedQuestion> passedQuestions = new HashSet<>();
        passedTest.setPassedQuestions(passedQuestions);
        PassedQuestion currentPassedQuestion = new PassedQuestion();
        List<UserAnswer> userAnswers = new ArrayList<>();
        double totalRating = 0.0;
        for (AnswerInOut oneAnswer : incomingQuestionsWithUserAnswer) {
            userAnswers.clear();
            optionalQuestion = questionRepo.findById(oneAnswer.getQuestionId());
            if (!optionalQuestion.isPresent()) {
                answerInOutList.add(new AnswerInOut(oneAnswer.getQuestionId(), null));
            }
            currentPassedQuestion = new PassedQuestion();
            currentCheckingQuestion = optionalQuestion.get();
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
                    answerInOutList.add(checkedQuestion.getAnswerInOut());
                    break;
                case SEQUENCE:
                    checkedQuestion = checkSequenceTypeQuestion(currentCheckingQuestion,
                            oneAnswer.getAnswers(), currentPassedQuestion, userAnswers);
                    totalRating += checkedQuestion.getRating();
                    answerInOutList.add(checkedQuestion.getAnswerInOut());
                    break;
            }
            addAPassedQuestion(passedTest, passedQuestions, currentPassedQuestion, userAnswers);
        }
        int resInPercent = (int) Math.round(totalRating * 100.0 / incomingQuestionsWithUserAnswer.size());
        ResultOut res = new ResultOut(answerInOutList, resInPercent);
        saveTest(userContext, passedTest, passedQuestions, resInPercent);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    private void addAPassedQuestion(PassedTest passedTest, Set<PassedQuestion> passedQuestions, PassedQuestion currentPassedQuestion, List<UserAnswer> userAnswers) {
        currentPassedQuestion.setUserAnswers(new HashSet<>(userAnswers));
        currentPassedQuestion.setPassedTest(passedTest);
        passedQuestions.add(currentPassedQuestion);
    }

    private void saveTest(UserContext userContext, PassedTest passedTest, Set<PassedQuestion> passedQuestions, int resInPercent) {
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

    public ResponseEntity<?> fetchUserPassedTest(String token) {
        return requestHandlerService.proceed(token, userContext -> formUserPassedTest(userContext),
                EnumSet.of(UserRole.ADMIN, UserRole.TEACHER, UserRole.USER));

    }

    private ResponseEntity<?> formUserPassedTest(UserContext userContext) {
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

    public ResponseEntity<?> fetchSubjectThemes(Long subjectId, String token) {
        return requestHandlerService.proceed(token, userContext -> {
                    Subject tempSubject = (subjectRepo.findById(subjectId)
                            .orElse(null));
                    if (tempSubject == null) {
                        String response = String.format("Предмета" + doesntExistById, subjectId);
                        return new ResponseEntity<>(new Message(response), HttpStatus.UNPROCESSABLE_ENTITY);
                    }
                    List<Theme> themes = themeRepo.findAllBySubjectId(subjectId);
                    return new ResponseEntity<>(themes, HttpStatus.OK);
                },
                EnumSet.of(UserRole.ADMIN, UserRole.TEACHER, UserRole.USER));

    }


//    public ResponseEntity<?> findAllBySubjectAndTheme(String subject, String theme, String token) {
//        return requestHandlerService.proceed(token, userContext -> fetchAllQuestionsFromDB(subject, theme),
//                EnumSet.of(UserRole.ADMIN, UserRole.TEACHER));
//    }
//
//    private ResponseEntity<?> fetchAllQuestionsFromDB(String subject, String theme) {
//        Optional<Subject> tempSubject = subjectRepo.findByName(subject);
//        if (!tempSubject.isPresent()) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        Integer idOfSubject = tempSubject.get().getId();
//        Iterable<Question> questions = questionRepo.findAllByIdAndTheme(idOfSubject.longValue(), theme);
//        return new ResponseEntity<>(questions, HttpStatus.OK);
//    }

    private User fetchUser(UserContext userContext) {
        Optional<User> user = userRepo.findById(userContext.getUserId());
        return user.orElse(null);
    }

}
