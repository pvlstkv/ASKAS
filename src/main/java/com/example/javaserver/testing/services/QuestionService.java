package com.example.javaserver.testing.services;

import com.example.javaserver.common_data.model.Subject;
import com.example.javaserver.common_data.model.Theme;
import com.example.javaserver.common_data.repo.SubjectRepo;
import com.example.javaserver.general.model.Message;
import com.example.javaserver.testing.models.Question;
import com.example.javaserver.testing.models.dto.QuestionIn;
import com.example.javaserver.testing.models.dto.TestIn;
import com.example.javaserver.testing.repo.QuestionRepo;
import com.example.javaserver.testing.repo.ThemeRepo;
import com.example.javaserver.testing.services.models.ResultOfSomethingChecking;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepo questionRepo;

    private final SubjectRepo subjectRepo;

    private final ThemeRepo themeRepo;

    private final String doesntExistById = " с id %d в базе данных не существует. " +
            "Пожалуйста проверьте корретность введенных данных.";


    public QuestionService(QuestionRepo questionRepo, SubjectRepo subjectRepo, ThemeRepo themeRepo, ResultOfSomethingChecking result) {
        this.questionRepo = questionRepo;
        this.subjectRepo = subjectRepo;
        this.themeRepo = themeRepo;
    }


    public ResponseEntity<?> createQuestions(TestIn testIn) {
        ResultOfSomethingChecking checkResult = new ResultOfSomethingChecking();
        // it's need to check a subject and theme
        ResultOfSomethingChecking.checkIfExistsInDB(new Subject(testIn.getSubjectId()), subjectRepo, checkResult);
        ResultOfSomethingChecking.checkIfExistsInDB(new Theme(testIn.getThemeId()), themeRepo, checkResult);
        if (!checkResult.getItsOK()) return checkResult.getResponseEntity();
        Question newQuestion;
        for (QuestionIn oneRawQuestion : testIn.getQuestionIns()) {
            // можно добавить проверку на существовние добаляемого вопроса
            newQuestion = new Question(oneRawQuestion, checkResult.getSubject(), checkResult.getTheme());
            Question finalNewQuestion = newQuestion;
            newQuestion.getAnswerChoiceList().forEach(answerChoice -> answerChoice.setQuestion(finalNewQuestion));
            questionRepo.save(newQuestion);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public ResponseEntity<?> updateQuestions(TestIn testIn) {
        ResultOfSomethingChecking checkResult = new ResultOfSomethingChecking();
        // it's need to check a subject and theme
        ResultOfSomethingChecking.checkIfExistsInDB(new Subject(testIn.getSubjectId()), subjectRepo, checkResult);
        ResultOfSomethingChecking.checkIfExistsInDB(new Theme(testIn.getThemeId()), themeRepo, checkResult);
        if (!checkResult.getItsOK()) return checkResult.getResponseEntity();
        Question newQuestion;
        for (QuestionIn oneRawQuestion : testIn.getQuestionIns()) {
            ResultOfSomethingChecking.checkIfExistsInDB(new Question(oneRawQuestion.getId()), questionRepo, checkResult);
            if (!checkResult.getItsOK()) {
                return checkResult.getResponseEntity();
            }
            newQuestion = new Question(oneRawQuestion, checkResult.getSubject(), checkResult.getTheme());
            Question finalNewQuestion = newQuestion;
            newQuestion.getAnswerChoiceList().forEach(answerChoice -> answerChoice.setQuestion(finalNewQuestion));
            questionRepo.save(newQuestion);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


    public ResponseEntity<?> deleteManyQuestions(List<Long> ids) {
        for (Long id : ids) {
            questionRepo.deleteById(id);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<?> fetchSubjectThemes(Long subjectId) {
        Subject tempSubject = (subjectRepo.findById(subjectId)
                .orElse(null));
        if (tempSubject == null) {
            String response = String.format("Предмета" + doesntExistById, subjectId);
            return new ResponseEntity<>(new Message(response), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        List<Theme> themes = themeRepo.findAllBySubjectId(subjectId);
        return new ResponseEntity<>(themes, HttpStatus.OK);
    }


    public ResponseEntity<?> deleteAllQuestions() {
        questionRepo.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }


    public ResponseEntity<?> fetchAllQuestions() {
        List<Question> question = questionRepo.findAll();
        return new ResponseEntity<>(question, HttpStatus.OK);
    }

//    private ResultOfSubjectAndThemeChecking checkSubjectAndTheme(Long subjectId, Long themeId) {
//        Subject tempSubject = (subjectRepo.findById(subjectId)
//                .orElse(null));
//        ResultOfSubjectAndThemeChecking result = new ResultOfSubjectAndThemeChecking();
//        if (tempSubject == null) {
//            String response = String.format("Предмета" + doesntExistById, subjectId);
//            result.setItsOK(false);
//            result.setResponseEntity(new ResponseEntity<>(new Message(response), HttpStatus.UNPROCESSABLE_ENTITY));
//            return result;
//        }
//        Optional<Theme> theme = themeRepo.findById(themeId);
//        if (!theme.isPresent()) {
//            String response = String.format("Темы" + doesntExistById, themeId);
//            result.setItsOK(false);
//            result.setResponseEntity(new ResponseEntity<>(new Message(response), HttpStatus.UNPROCESSABLE_ENTITY));
//            return result;
//        }
//        result.setItsOK(true);
//        result.setSubject(tempSubject);
//        result.setTheme(theme.get());
//        return result;
//    }

//    private <T> void checkIfExistsInDB(T something, ResultOfSubjectAndThemeChecking result) {
//        if (something instanceof Subject) {
//            Subject tempSubject = (subjectRepo.findById(((Subject) something).getId()).orElse(null));
//            if (tempSubject == null) {
//                String response = String.format("Предмета" + doesntExistById, ((Subject) something).getId());
//                result.setItsOK(false);
//                result.setResponseEntity(new ResponseEntity<>(new Message(response), HttpStatus.UNPROCESSABLE_ENTITY));
//            }
//            result.setItsOK(true);
//            result.setSubject(tempSubject);
//        }
//        if (something instanceof Theme) {
//            Optional<Theme> theme = themeRepo.findById(((Theme) something).getId());
//            if (!theme.isPresent()) {
//                String response = String.format("Темы" + doesntExistById, ((Theme) something).getId());
//                result.setItsOK(false);
//                result.setResponseEntity(new ResponseEntity<>(new Message(response), HttpStatus.UNPROCESSABLE_ENTITY));
//            }
//            result.setItsOK(true);
//            result.setTheme(theme.get());
//        }
//        if (something instanceof Question) {
//            Optional<Question> question = questionRepo.findById(((Question) something).getId());
//            if (!question.isPresent()) {
//                String response = String.format("Вопроса" + doesntExistById, ((Question) something).getId());
//                result.setItsOK(false);
//                result.setResponseEntity(new ResponseEntity<>(new Message(response), HttpStatus.UNPROCESSABLE_ENTITY));
//            }
//            result.setItsOK(true);
//            result.setQuestion(question.get());
//        }
//        result.setItsOK(false);
//    }
//}
//
//class ResultOfSubjectAndThemeChecking {
//    private Subject subject;
//    private Theme theme;
//    private Question question;
//    private ResponseEntity<?> responseEntity;
//    private Boolean itsOK;
//
//    public ResultOfSubjectAndThemeChecking() {
//    }
//
//    public Boolean getItsOK() {
//        return itsOK;
//    }
//
//    public void setItsOK(Boolean itsOK) {
//        this.itsOK = itsOK;
//    }
//
//    public Question getQuestion() {
//        return question;
//    }
//
//    public void setQuestion(Question question) {
//        this.question = question;
//    }
//
//    public Subject getSubject() {
//        return subject;
//    }
//
//    public void setSubject(Subject subject) {
//        this.subject = subject;
//    }
//
//    public Theme getTheme() {
//        return theme;
//    }
//
//    public void setTheme(Theme theme) {
//        this.theme = theme;
//    }
//
//    public ResponseEntity<?> getResponseEntity() {
//        return responseEntity;
//    }
//
//    public void setResponseEntity(ResponseEntity<?> responseEntity) {
//        this.responseEntity = responseEntity;
//    }
}
