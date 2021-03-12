package com.example.javaserver.testing.services.models;

import com.example.javaserver.common_data.model.Subject;
import com.example.javaserver.common_data.model.Theme;
import com.example.javaserver.common_data.repo.SubjectRepo;
import com.example.javaserver.general.model.Message;
import com.example.javaserver.testing.models.Question;
import com.example.javaserver.testing.repo.QuestionRepo;
import com.example.javaserver.testing.repo.ThemeRepo;
import com.example.javaserver.user.model.User;
import com.example.javaserver.user.repo.UserRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ResultOfSomethingChecking {
    private Subject subject;
    private Theme theme;
    private Question question;
    private User user;
    private ResponseEntity<?> responseEntity;
    private Boolean itsOK;

    //    private static <T, K extends GetIdAble> void checkIfExistsInDB(T something, K  repo, ResultOfSomethingChecking result) {
    public static <T, K> void checkIfExistsInDB(T something, K repo, ResultOfSomethingChecking result) {
        String doesntExistById = " с id %d в базе данных не существует. " +
                "Пожалуйста проверьте корретность введенных данных.";
//        try {
//            ((CrudRepository<T,Long>)repo).existsById(((GetIdAble)something).getId());
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        if ((something instanceof Subject) && (repo instanceof SubjectRepo)) {
            Subject tempSubject = (((SubjectRepo) repo).findById(((Subject) something).getId()).orElse(null));
            if (tempSubject == null) {
                String response = String.format("Предмета" + doesntExistById, ((Subject) something).getId());
                result.setItsOK(false);
                result.setResponseEntity(new ResponseEntity<>(new Message(response), HttpStatus.UNPROCESSABLE_ENTITY));
            }
            result.setItsOK(true);
            result.setSubject(tempSubject);
            return;
        }
        if (something instanceof Theme && repo instanceof ThemeRepo) {
            Theme theme = ((ThemeRepo) repo).findById(((Theme) something).getId()).orElse(null);
            if (theme == null) {
                String response = String.format("Темы" + doesntExistById, ((Theme) something).getId());
                result.setItsOK(false);
                result.setResponseEntity(new ResponseEntity<>(new Message(response), HttpStatus.UNPROCESSABLE_ENTITY));
            }
            result.setItsOK(true);
            result.setTheme(theme);
            return;
        }
        if (something instanceof Question && repo instanceof QuestionRepo) {
            Question question = ((QuestionRepo) repo).findById(((Question) something).getId()).orElse(null);
            if (question == null) {
                String response = String.format("Вопроса" + doesntExistById, ((Question) something).getId());
                result.setItsOK(false);
                result.setResponseEntity(new ResponseEntity<>(new Message(response), HttpStatus.UNPROCESSABLE_ENTITY));
            }
            result.setItsOK(true);
            result.setQuestion(question);
            return;
        }
        if (something instanceof User && repo instanceof UserRepo) {
            User user = ((UserRepo) repo).findById(((User) something).getId()).orElse(null);
            if (user == null) {
                String response = String.format("Пользователя" + doesntExistById, ((User) something).getId());
                result.setItsOK(false);
                result.setResponseEntity(new ResponseEntity<>(new Message(response), HttpStatus.UNPROCESSABLE_ENTITY));
            }
            result.setItsOK(true);
            result.setUser(user);
            return;
        }
        result.setItsOK(false);
    }

    public ResultOfSomethingChecking() {
    }

    public ResultOfSomethingChecking(Subject subject, Theme theme, Question question, User user, ResponseEntity<?> responseEntity, Boolean itsOK) {
        this.subject = subject;
        this.theme = theme;
        this.question = question;
        this.user = user;
        this.responseEntity = responseEntity;
        this.itsOK = itsOK;
    }

    public Boolean getItsOK() {
        return itsOK;
    }

    public void setItsOK(Boolean itsOK) {
        this.itsOK = itsOK;
    }

    public Question getQuestion() {
        return question;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public ResponseEntity<?> getResponseEntity() {
        return responseEntity;
    }

    public void setResponseEntity(ResponseEntity<?> responseEntity) {
        this.responseEntity = responseEntity;
    }
}
