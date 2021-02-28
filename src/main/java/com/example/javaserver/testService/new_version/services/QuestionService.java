package com.example.javaserver.testService.new_version.services;

import com.example.javaserver.common_data.model.Subject;
import com.example.javaserver.common_data.repo.SubjectRepo;
import com.example.javaserver.general.model.Message;
import com.example.javaserver.general.service.JwtService;
import com.example.javaserver.general.service.RequestHandlerService;
import com.example.javaserver.testService.new_version.models.AnswerChoice;
import com.example.javaserver.testService.new_version.models.InOutComingModels.*;
import com.example.javaserver.testService.new_version.models.Question;
import com.example.javaserver.testService.new_version.models.UserAnswer;
import com.example.javaserver.testService.new_version.models.saving_results.PassedTest;
import com.example.javaserver.testService.new_version.repo.QuestionRepo;
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
    private JwtService jwtService;

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
        Subject tempSubject = (subjectRepo.findById(testIn.getSubjectId())
                .orElse(null));
        if (tempSubject == null) {
            String response = String.format("Предмета" + doesntExistById, testIn.getSubjectId());
            return new ResponseEntity<>(new Message(response), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        for (QuestionIn oneRawQuestion : testIn.getRequestedQuestions()) {
            // можно добавить проверку на существовние добаляемого вопроса
            newQuestion = new Question(oneRawQuestion, tempSubject);
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
        for (QuestionIn oneRawQuestion : testIn.getRequestedQuestions()) {
            oldQuestion = questionRepo.findById(oneRawQuestion.getId());
            if (!oldQuestion.isPresent()) {
                String response = String.format("Вопроса" + doesntExistById, testIn.getSubjectId());
                return new ResponseEntity<>(new Message(response), HttpStatus.UNPROCESSABLE_ENTITY);
            }
            newQuestion = new Question(oneRawQuestion, tempSubject);
            questionRepo.saveAndFlush(newQuestion);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<?> deleteManyQuestions(List<Long> ids, String token) {
        return requestHandlerService.proceed(token, userContext -> removeQuestionsInTheDB(ids),
                EnumSet.of(UserRole.ADMIN, UserRole.TEACHER));
    }

    private ResponseEntity<?> removeQuestionsInTheDB(List<Long> ids) {
        for (Long id : ids) {
            questionRepo.deleteById(Math.toIntExact(id));
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


    public ResponseEntity<?> createTest(Long subjectId, String theme, int countOfQuestions, String token) {
        return requestHandlerService.proceed(token, userContext -> makeTestFromDBQuestions(subjectId, theme, countOfQuestions),
                EnumSet.of(UserRole.ADMIN, UserRole.TEACHER));
    }

    private ResponseEntity<?> makeTestFromDBQuestions(Long subjectId, String theme, int countOfQuestions) {

        if (countOfQuestions < 1)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Subject tempSubject = (subjectRepo.findById(subjectId))
                .orElse(null);
        if (tempSubject == null) {
            String response = String.format("Предмета" + doesntExistById, subjectId);
            return new ResponseEntity<>(new Message(response), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        List<Question> questions4Test = questionRepo.findAllByIdAndTheme(subjectId, theme);
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
        return requestHandlerService.proceed(token, userContext -> generateTestResult(userTest),
                EnumSet.of(UserRole.ADMIN, UserRole.TEACHER));
    }

    private ResponseEntity<ResultOut> generateTestResult(List<AnswerInOut> requestedAnswers) {
        // checking answers //проверка ответов
        List<Object> resultsOfChecking = checkingAnswers(requestedAnswers);
        double rightAnswersCount = (double) resultsOfChecking.get(2);
        long resInPercent = Math.round(rightAnswersCount * 100.0 / requestedAnswers.size());
        ResultOut resultOut = new ResultOut(
                (List<AnswerInOut>) resultsOfChecking.get(1),
                resInPercent);
        return new ResponseEntity<>(submittedResult, HttpStatus.OK);
    }

    //todo refactor this method and submitted models!!!!!

    /**
     * functiob of checking answers of questions
     *
     * @param requestedAnswers a list if user answers
     * @return a list of 3 objects: 0 - the name of subject, 1 - the list of checked answers, 2 - the value of right answers
     */
    private List<Object> checkingAnswers(List<AnswerInOut> requestedAnswers) {
        List<Object> resultsOfChecking = new ArrayList<>();
        double rightAnswersCounter = 0;
        Question currentCheckingQuestion; // question from db
        List<String> listOfRightAnswers = new ArrayList<>();
        List<String> listOfUserAnswers;
        List<AnswerInOut> rightAnswers = new ArrayList<>();
        // use switch case to check any type questions
        List<QuesitonResultOut> submittedResultQuestions = new ArrayList<>();
        List<UserAnswer> checkedUserAnswers = new ArrayList<>();
        PassedTest passedTest = new PassedTest();
        boolean isFounded = false;
        for (AnswerInOut currentUserAnswer : requestedAnswers) {
            currentCheckingQuestion = questionRepo.findById(currentUserAnswer.getQuestionId()).get();

            // if user gave answers equal or less than in the original question
            currentCheckingQuestion.getAnswerChoiceList()
                    .stream().filter(AnswerChoice::getRight).
                    forEach(oneAnswer -> listOfRightAnswers.add(oneAnswer.getAnswer()));
            listOfUserAnswers = currentUserAnswer.getAnswers();
            if (listOfRightAnswers.size() == 0)
                listOfRightAnswers.add("");
            for (String userAnswer : listOfUserAnswers) {
                isFounded = false; // is founded an user answer among right answers
                for (String rightAnswer : listOfRightAnswers) {
                    if (rightAnswer.equals(userAnswer)) {
                        // if user gave right answers more than in the original question, then the answer is making true and adding points
                        if (currentUserAnswer.getAnswers().size() <= listOfRightAnswers.size()) {
                            rightAnswersCounter += 1.0 / listOfRightAnswers.size();
                            checkedUserAnswers.add(new UserAnswer(userAnswer, true));
                        } else { // if user gave right answers more than in the original question, then answers is making false
                            checkedUserAnswers.add(new UserAnswer(userAnswer, false));
                        }
                        isFounded = true;
                        break;
                    }
                }
                if (!isFounded) {
                    checkedUserAnswers.add(new UserAnswer(userAnswer, false));
                }
            }
            submittedResultQuestions.add(new QuesitonResultOut(currentCheckingQuestion, new ArrayList<>(checkedUserAnswers)));
            checkedUserAnswers.clear();
            listOfRightAnswers.clear();
        }

        resultsOfChecking.add(questionRepo.findById(requestedAnswers.get(0).
                getQuestionId()).get().getSubject().getName()); // the name of subject
        resultsOfChecking.add(submittedResultQuestions); // checked answers
        resultsOfChecking.add(rightAnswersCounter); // the value of right answers

        return resultsOfChecking;
    }


    public ResponseEntity<?> findAllBySubjectAndTheme(String subject, String theme, String token) {
        return requestHandlerService.proceed(token, userContext -> fetchAllQuestionsFromDB(subject, theme),
                EnumSet.of(UserRole.ADMIN, UserRole.TEACHER));
    }

    private ResponseEntity<?> fetchAllQuestionsFromDB(String subject, String theme) {
        Optional<Subject> tempSubject = subjectRepo.findByName(subject);
        if (!tempSubject.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Integer idOfSubject = tempSubject.get().getId();
        Iterable<Question> questions = questionRepo.findAllByIdAndTheme(idOfSubject.longValue(), theme);
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

}
