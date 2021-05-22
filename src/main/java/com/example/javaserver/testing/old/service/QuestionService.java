package com.example.javaserver.testing.old.service;

import com.example.javaserver.common_data.model.Subject;
import com.example.javaserver.common_data.repo.SubjectRepo;
import com.example.javaserver.testing.old.model.Question;
import com.example.javaserver.testing.old.model.dto.QuestionIn;
import com.example.javaserver.testing.old.model.dto.TestIn;
import com.example.javaserver.testing.old.repo.QuestionRepo;
import com.example.javaserver.testing.old.service.model.ResultOfSomethingChecking;
import com.example.javaserver.testing.theme.Theme;
import com.example.javaserver.testing.theme.ThemeRepo;
import org.springframework.beans.factory.annotation.Autowired;
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


    public void createQuestions(TestIn testIn) {
        // it's need to check a subject and theme
        ResultOfSomethingChecking checkResult = new ResultOfSomethingChecking();
        checkResult = checkResult.checkIfExistsInDB(new Subject(testIn.getSubjectId()), subjectRepo, checkResult);
        checkResult = checkResult.checkIfExistsInDB(new Theme(testIn.getThemeId()), themeRepo, checkResult);
        for (QuestionIn oneRawQuestion : testIn.getQuestionIns()) {
            // можно добавить проверку на существовние добаляемого вопроса
            Question newQuestion = new Question(oneRawQuestion, checkResult.getSubject(), checkResult.getTheme());
            newQuestion.getAnswerChoiceList().forEach(answerChoice -> answerChoice.setQuestion(newQuestion));
            questionRepo.save(newQuestion);
        }

    }

    public void updateQuestions(TestIn testIn) {
        ResultOfSomethingChecking checkResult = new ResultOfSomethingChecking();
        // it's need to check a subject and theme
        checkResult = checkResult.checkIfExistsInDB(new Subject(testIn.getSubjectId()), subjectRepo, checkResult);
        checkResult = checkResult.checkIfExistsInDB(new Theme(testIn.getThemeId()), themeRepo, checkResult);
        for (QuestionIn oneRawQuestion : testIn.getQuestionIns()) {
            checkResult = checkResult.checkIfExistsInDB(new Question(oneRawQuestion.getId()), questionRepo, checkResult);
            Question newQuestion = new Question(oneRawQuestion, checkResult.getSubject(), checkResult.getTheme());
            newQuestion.getAnswerChoiceList().forEach(answerChoice -> answerChoice.setQuestion(newQuestion));
            questionRepo.save(newQuestion);
        }
    }
    
    public void deleteManyQuestions(List<Long> ids) {
        ids.forEach(questionRepo::deleteById);
    }

    public void deleteAllQuestions() {
        questionRepo.deleteAll();
    }

    public List<Question> fetchAllQuestions(Long themeId) {
        return questionRepo.findAllByThemeId(themeId);
    }
}
