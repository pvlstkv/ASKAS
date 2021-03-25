package com.example.javaserver.testing.service;

import com.example.javaserver.common_data.model.Subject;
import com.example.javaserver.testing.model.Theme;
import com.example.javaserver.common_data.repo.SubjectRepo;
import com.example.javaserver.general.model.Message;
import com.example.javaserver.testing.model.Question;
import com.example.javaserver.testing.model.dto.QuestionIn;
import com.example.javaserver.testing.model.dto.TestIn;
import com.example.javaserver.testing.repo.QuestionRepo;
import com.example.javaserver.testing.repo.ThemeRepo;
import com.example.javaserver.testing.service.model.ResultOfSomethingChecking;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public QuestionService(QuestionRepo questionRepo, SubjectRepo subjectRepo, ThemeRepo themeRepo, ResultOfSomethingChecking result) {
        this.questionRepo = questionRepo;
        this.subjectRepo = subjectRepo;
        this.themeRepo = themeRepo;
    }


    public ResponseEntity<?> createQuestions(TestIn testIn) {

        // it's need to check a subject and theme
        ResultOfSomethingChecking checkResult;
        checkResult = ResultOfSomethingChecking.checkIfExistsInDB(new Subject(testIn.getSubjectId()), subjectRepo);
        checkResult = ResultOfSomethingChecking.checkIfExistsInDB(new Theme(testIn.getThemeId()), themeRepo);
        if (!checkResult.getItsOK()) return checkResult.getResponseEntity();
        for (QuestionIn oneRawQuestion : testIn.getQuestionIns()) {
            // можно добавить проверку на существовние добаляемого вопроса
            Question newQuestion = new Question(oneRawQuestion, checkResult.getSubject(), checkResult.getTheme());
            newQuestion.getAnswerChoiceList().forEach(answerChoice -> answerChoice.setQuestion(newQuestion));
            questionRepo.save(newQuestion);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public ResponseEntity<?> updateQuestions(TestIn testIn) {
        ResultOfSomethingChecking checkResult;
        // it's need to check a subject and theme
        checkResult = ResultOfSomethingChecking.checkIfExistsInDB(new Subject(testIn.getSubjectId()), subjectRepo);
        checkResult = ResultOfSomethingChecking.checkIfExistsInDB(new Theme(testIn.getThemeId()), themeRepo);
        if (!checkResult.getItsOK()) return checkResult.getResponseEntity();
        Question newQuestion;
        for (QuestionIn oneRawQuestion : testIn.getQuestionIns()) {
            checkResult = ResultOfSomethingChecking.checkIfExistsInDB(new Question(oneRawQuestion.getId()), questionRepo);
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

    public ResponseEntity<?> deleteAllQuestions() {
        questionRepo.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }


    public ResponseEntity<?> fetchAllQuestions() {
        List<Question> question = questionRepo.findAll();
        return new ResponseEntity<>(question, HttpStatus.OK);
    }

}
