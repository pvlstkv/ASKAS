package com.example.javaserver.testService.new_version.services;

import com.example.javaserver.common_data.model.Subject;
import com.example.javaserver.common_data.model.Theme;
import com.example.javaserver.common_data.repo.SubjectRepo;
import com.example.javaserver.general.model.Message;
import com.example.javaserver.general.service.JwtService;
import com.example.javaserver.general.service.RequestHandlerService;
import com.example.javaserver.testService.new_version.models.AnswerChoice;
import com.example.javaserver.testService.new_version.models.InOutComingModels.*;
import com.example.javaserver.testService.new_version.models.Question;
import com.example.javaserver.testService.new_version.models.UserAnswer;
//import com.example.javaserver.testService.new_version.models.saving_results.PassedTest;
import com.example.javaserver.testService.new_version.repo.QuestionRepo;
import com.example.javaserver.testService.new_version.repo.ThemeRepo;
import com.example.javaserver.user.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QuestionService {

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
        if (!theme.isPresent()){
            String response = String.format("Темы" + doesntExistById, testIn.getThemeId());
            return new ResponseEntity<>(new Message(response), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        for (QuestionIn oneRawQuestion : testIn.getQuestionIns()) {
            // можно добавить проверку на существовние добаляемого вопроса
            newQuestion = new Question(oneRawQuestion, tempSubject, theme.get());
            Question finalNewQuestion = newQuestion;
            newQuestion.getAnswerChoiceList().stream().forEach(answerChoice -> answerChoice.setQuestion(finalNewQuestion));
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
        if (!theme.isPresent()){
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
                EnumSet.of(UserRole.ADMIN, UserRole.TEACHER));
    }

    private ResponseEntity<?> makeTestFromDBQuestions(Long subjectId, Long themeId, int countOfQuestions) {

        if (countOfQuestions < 1)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        Subject tempSubject = (subjectRepo.findById(subjectId))
//                .orElse(null);
//        if (tempSubject == null) {
//            String response = String.format("Предмета" + doesntExistById, subjectId);
//            return new ResponseEntity<>(new Message(response), HttpStatus.UNPROCESSABLE_ENTITY);
//        }
        Optional<Theme> theme = themeRepo.findById(themeId);
        if (!theme.isPresent()){
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
        return requestHandlerService.proceed(token, userContext -> checkingAnswers(userTest),
                EnumSet.of(UserRole.ADMIN, UserRole.TEACHER));
    }

    private ResponseEntity<ResultOut> checkingAnswers(List<AnswerInOut> incomingQuestionsWithUserAnswer) {
        Question currentCheckingQuestion;
        Optional<Question> optionalQuestion;
        CheckedQuestion checkedQuestion;
        List<AnswerInOut> answerInOutList = new ArrayList<>();
        double totalRating = 0.0;
        for (AnswerInOut oneAnswer : incomingQuestionsWithUserAnswer) {
            optionalQuestion = questionRepo.findById(oneAnswer.getQuestionId());
            if (!optionalQuestion.isPresent()) {
                answerInOutList.add(new AnswerInOut(oneAnswer.getQuestionId(), null));
            }
            currentCheckingQuestion = optionalQuestion.get();
            switch (currentCheckingQuestion.getQuestionType()) {
                case MATCH:
                    // todo there are some problems...
                    break;
                case WRITE:
                case CHOOSE:
                    checkedQuestion = checkChooseAndWriteTypeQuestion(currentCheckingQuestion, oneAnswer.getAnswers());
                    totalRating += checkedQuestion.getRating();
                    answerInOutList.add(checkedQuestion.getAnswerInOut());
                    break;
                case SEQUENCE:
                    checkedQuestion = checkSequenceTypeQuestion(currentCheckingQuestion, oneAnswer.getAnswers());
                    totalRating += checkedQuestion.getRating();
                    answerInOutList.add(checkedQuestion.getAnswerInOut());
                    break;
            }
        }
        long resInPersent = Math.round(totalRating * 100.0 / incomingQuestionsWithUserAnswer.size());
        ResultOut res = new ResultOut(answerInOutList, resInPersent);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    private CheckedQuestion checkChooseAndWriteTypeQuestion(Question checkingQuestion,
                                                            List<String> listOfUserAnswers) {
        double rightAnswersCounter = 0;
        List<String> listOfRightAnswers = checkingQuestion.fetchRightAnswers();
        for (String userAnswer : listOfUserAnswers) {
            for (String rightAnswer : listOfRightAnswers) {
                if (rightAnswer.equals(userAnswer)) {
                    // if user gave right answers more than in the original question, then the answer is making true and adding points
                    if (listOfUserAnswers.size() <= listOfRightAnswers.size()) {
                        rightAnswersCounter += 1.0 / listOfRightAnswers.size();
                    }
                    break;
                }
            }
        }
        AnswerInOut out = new AnswerInOut(checkingQuestion.getId(), listOfRightAnswers);
        return new CheckedQuestion(out, rightAnswersCounter);
    }

    private CheckedQuestion checkSequenceTypeQuestion(Question checkingQuestion, List<String> listOfUserAnswers) {
        double rightAnswersCounter = 0;
        List<String> rightSequence = checkingQuestion.fetchRightAnswers();
        int sizeRS = rightSequence.size();
        if (listOfUserAnswers.size() != sizeRS)
            return new CheckedQuestion(new AnswerInOut(checkingQuestion.getId(), listOfUserAnswers), 0.0);
        for (int i = 0; i < sizeRS; i++) {
            if (rightSequence.get(i).equals(listOfUserAnswers.get(i))) {
                rightAnswersCounter += 1.0 / sizeRS;
            }
        }
        return new CheckedQuestion(new AnswerInOut(checkingQuestion.getId(), rightSequence), rightAnswersCounter);
    }

//    private CheckedQuestion checkMatchTypeQuestions(Question checkingQuestion, List<String> listOfUserAnswers){
//
//    }

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

}
