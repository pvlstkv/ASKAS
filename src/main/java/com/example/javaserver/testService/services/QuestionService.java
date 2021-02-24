package com.example.javaserver.testService.services;

import com.example.javaserver.basemodel.ContextUser;
import com.example.javaserver.model.Subject;
import com.example.javaserver.service.JwtService;
//import com.example.javaserver.testService.models.AnswerChoice;
import com.example.javaserver.testService.models.AnswerChoice;
import com.example.javaserver.testService.models.InOutComingModels.*;
//import com.example.javaserver.testService.models.Question;
import com.example.javaserver.config.JwtUtil;

import com.example.javaserver.testService.models.Question;
import com.example.javaserver.testService.models.UserAnswer;
//import com.example.javaserver.testService.repo.QuestionRepo;
import com.example.javaserver.testService.repo.QuestionRepo;
import com.example.javaserver.testService.repo.SubjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.ExpiredJwtException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepo questionRepo;

    @Autowired
    private SubjectRepo subjectRepo;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private JwtService jwtService;


    public ResponseEntity<?> createQuestions(RequestedTest requestedTest, String token) {
        try {
            ContextUser contextUser = jwtService.modelAuth(token);
            if (!jwtService.isAdminOrTeacher(contextUser))
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);

            Question newQuestion;
            Subject tempSubject = (subjectRepo.findByName(requestedTest.getSubject())
                    .orElse(new Subject(requestedTest.getSubject())));
            for (RequestedQuestion oneRawQuestion : requestedTest.getRequestedQuestions()) {
                // можно добавить проверку на существовние добаляемого вопроса
                newQuestion = new Question(oneRawQuestion, tempSubject);
                Question finalNewQuestion = newQuestion;
                newQuestion.getAnswerChoiceList().stream().forEach(answerChoice -> answerChoice.setQuestion(finalNewQuestion));
                questionRepo.save(newQuestion);
            }
            return new ResponseEntity<>(HttpStatus.CREATED);

        } catch (ExpiredJwtException e) {
            return new ResponseEntity<>(HttpStatus.UPGRADE_REQUIRED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }


    }

    public ResponseEntity<?> updateQuestions(RequestedTest requestedTest, String token) {
        try {
            ContextUser contextUser = jwtService.modelAuth(token);
            if (!jwtService.isAdminOrTeacher(contextUser))
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);

            Optional<Question> oldQuestion;
            Question newQuestion;
            Subject tempSubject = (subjectRepo.findByName(requestedTest.getSubject())
                    .orElse(new Subject(requestedTest.getSubject())));
            for (RequestedQuestion oneRawQuestion : requestedTest.getRequestedQuestions()) {
                oldQuestion = questionRepo.findById(oneRawQuestion.getId());
                if (!oldQuestion.isPresent()) {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
                newQuestion = new Question(oneRawQuestion, tempSubject);
                questionRepo.saveAndFlush(newQuestion);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ExpiredJwtException e) {
            return new ResponseEntity<>(HttpStatus.UPGRADE_REQUIRED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    public ResponseEntity<?> deleteManyQuestions(List<Long> ids, String token) {
        try {
            ContextUser contextUser = jwtService.modelAuth(token);
            if (!jwtService.isAdminOrTeacher(contextUser))
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            for (Long id : ids) {
                questionRepo.deleteById(Math.toIntExact(id));
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ExpiredJwtException e) {
            return new ResponseEntity<>(HttpStatus.UPGRADE_REQUIRED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }


    public ResponseEntity<?> deleteAllQuestions(String token) {
        try {
            ContextUser contextUser = jwtService.modelAuth(token);
            if (!jwtService.isAdminOrTeacher(contextUser))
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);

            questionRepo.deleteAll();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ExpiredJwtException e) {
            return new ResponseEntity<>(HttpStatus.UPGRADE_REQUIRED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }


    public ResponseEntity<?> createTest(String subjectName, int countOfQuestions, String token) {
        try {
            ContextUser contextUser = jwtService.modelAuth(token);
            if (!jwtService.isAdminOrTeacherOrStudent(contextUser))
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);

            if (countOfQuestions < 1)
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            Optional<Subject> subject = subjectRepo.findByName(subjectName);
            if (!subject.isPresent())
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            List<Question> questions4Test = questionRepo.findAllBySubjectId(subject.get().getId());
            Collections.shuffle(questions4Test);
            countOfQuestions = Math.min(countOfQuestions, questions4Test.size());
            questions4Test = questions4Test.subList(0, countOfQuestions);
            List<SubmittedQuestion> submittedQuestionList = new ArrayList<>();
            for (Question originalQuestion : questions4Test) {
                submittedQuestionList.add(new SubmittedQuestion(originalQuestion));
            }
            SubmittedTest submittedTest = new SubmittedTest(subjectName, submittedQuestionList);
            return new ResponseEntity<>(submittedTest, HttpStatus.OK);
        } catch (ExpiredJwtException e) {
            return new ResponseEntity<>(HttpStatus.UPGRADE_REQUIRED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    public ResponseEntity<?> checkTest(List<RequestedAnswer> requestedAnswers, String token) {
        try {
            ContextUser contextUser = jwtService.modelAuth(token);
            if (!jwtService.isAdminOrTeacherOrStudent(contextUser))
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);

            // checking answers //проверка ответов
            List<Object> resultsOfChecking = checkingAnswers(requestedAnswers);
            double rightAnswersCount = (double) resultsOfChecking.get(2);
            long resInPercent = Math.round(rightAnswersCount * 100.0 / requestedAnswers.size());
            SubmittedResult submittedResult = new SubmittedResult(
                    (String) resultsOfChecking.get(0),
                    (List<SubmittedResultQuestion>) resultsOfChecking.get(1),
                    resInPercent);
            return new ResponseEntity<>(submittedResult, HttpStatus.OK);
        } catch (ExpiredJwtException e) {
            return new ResponseEntity<>(HttpStatus.UPGRADE_REQUIRED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * functiob of checking answers of questions
     *
     * @param requestedAnswers a list if user answers
     * @return a list of 3 objects: 0 - the name of subject, 1 - the list of checked answers, 2 - the value of right answers
     */
    private List<Object> checkingAnswers(List<RequestedAnswer> requestedAnswers) {
        List<Object> resultsOfChecking = new ArrayList<>();
        double rightAnswersCounter = 0;
        Question currentCheckingQuestion; // question from db
        List<String> listOfRightAnswers = new ArrayList<>();
        List<String> listOfUserAnswers;
        List<SubmittedResultQuestion> submittedResultQuestions = new ArrayList<>();
        List<UserAnswer> checkedUserAnswers = new ArrayList<>();
        boolean isFounded = false;
        for (RequestedAnswer currentUserAnswer : requestedAnswers) {
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
            submittedResultQuestions.add(new SubmittedResultQuestion(currentCheckingQuestion, new ArrayList<>(checkedUserAnswers)));
            checkedUserAnswers.clear();
            listOfRightAnswers.clear();
        }

        resultsOfChecking.add(questionRepo.findById(requestedAnswers.get(0).
                getQuestionId()).get().getSubject().getName()); // the name of subject
        resultsOfChecking.add(submittedResultQuestions); // checked answers
        resultsOfChecking.add(rightAnswersCounter); // the value of right answers

        return resultsOfChecking;
    }


    public ResponseEntity<?> findAllBySubject(String subject, String token) {
        try {
            ContextUser contextUser = jwtService.modelAuth(token);
            if (!jwtService.isAdminOrTeacher(contextUser))
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);

            Optional<Subject> tempSubject = subjectRepo.findByName(subject);
            if (!tempSubject.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            Integer idOfSubject = tempSubject.get().getId();
            Iterable<Question> questions = questionRepo.findAllBySubjectId(idOfSubject);
            return new ResponseEntity<>(questions, HttpStatus.OK);

        } catch (ExpiredJwtException e) {
            return new ResponseEntity<>(HttpStatus.UPGRADE_REQUIRED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

}
