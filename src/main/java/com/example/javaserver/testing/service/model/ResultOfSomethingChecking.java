package com.example.javaserver.testing.service.model;

import com.example.javaserver.common_data.model.Subject;
import com.example.javaserver.testing.model.Theme;
import com.example.javaserver.common_data.repo.SubjectRepo;
import com.example.javaserver.general.model.Message;
import com.example.javaserver.testing.model.Question;
import com.example.javaserver.testing.repo.QuestionRepo;
import com.example.javaserver.testing.repo.ThemeRepo;
import com.example.javaserver.user.model.User;
import com.example.javaserver.user.repo.UserRepo;
import io.jsonwebtoken.Claims;
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
    private String errors;
    private static String doesntExistById = " с id %d в базе данных не существует. " +
            "Пожалуйста проверьте корретность введенных данных.\n";


    //    private static <T, K extends GetIdAble> void checkIfExistsInDB(T something, K  repo, ResultOfSomethingChecking result) {
    public static <T, K> ResultOfSomethingChecking checkIfExistsInDB(T something, K repo) {
        if ((something instanceof Subject) && (repo instanceof SubjectRepo)) {
            return checkSubject((Subject) something, (SubjectRepo) repo);
        }
        if (something instanceof Theme && repo instanceof ThemeRepo) {
            return checkTheme((Theme) something, (ThemeRepo) repo);
        }
        if (something instanceof Question && repo instanceof QuestionRepo) {
            return checkQuestion((Question) something, (QuestionRepo) repo);
        }
        if (something instanceof User && repo instanceof UserRepo) {
            return checkUser((User) something, (UserRepo) repo);
        }
        ResultOfSomethingChecking result = new ResultOfSomethingChecking();
        result.setItsOK(false);
        return result;
    }

    private static ResultOfSomethingChecking checkSubject(Subject subject, SubjectRepo repo) {
        ResultOfSomethingChecking result = new ResultOfSomethingChecking();
        subject = repo.findById(subject.getId()).orElse(null);
        if (subject == null) {
            String response = String.format("Предмета" + doesntExistById, subject.getId());
            result.errors += response;
            result.setItsOK(false);
            result.setResponseEntity(new ResponseEntity<>(new Message(result.errors), HttpStatus.UNPROCESSABLE_ENTITY));
        }
        result.setItsOK(true);
        result.setSubject(subject);
        return result;
    }

    private static ResultOfSomethingChecking checkTheme(Theme theme, ThemeRepo repo) {
        ResultOfSomethingChecking result = new ResultOfSomethingChecking();
        theme = repo.findById(theme.getId()).orElse(null);
        if (theme == null) {
            String response = String.format("Темы" + doesntExistById, theme.getId());
            result.errors += response;
            result.setItsOK(false);
            result.setResponseEntity(new ResponseEntity<>(new Message(result.errors), HttpStatus.UNPROCESSABLE_ENTITY));
        }
        result.setItsOK(true);
        result.setTheme(theme);
        return result;
    }

    private static ResultOfSomethingChecking checkQuestion(Question question, QuestionRepo repo) {
        ResultOfSomethingChecking result = new ResultOfSomethingChecking();
        question = repo.findById(question.getId()).orElse(null);
        if (question == null) {
            String response = String.format("Вопроса" + doesntExistById, question.getId());
            result.errors += response;
            result.setItsOK(false);
            result.setResponseEntity(new ResponseEntity<>(new Message(result.errors), HttpStatus.UNPROCESSABLE_ENTITY));
        }
        result.setItsOK(true);
        result.setQuestion(question);
        return result;
    }

    private static ResultOfSomethingChecking checkUser(User user, UserRepo repo) {
        ResultOfSomethingChecking result = new ResultOfSomethingChecking();
        User tempUser = repo.findById(user.getId()).orElse(null);
        if (tempUser == null) {
            String response = String.format("Пользователя" + doesntExistById, user.getId());
            result.errors += response;
            result.setItsOK(false);
            result.setResponseEntity(new ResponseEntity<>(new Message(result.errors), HttpStatus.UNPROCESSABLE_ENTITY));
        }
        result.setItsOK(true);
        result.setUser(user);
        return result;
    }

    public ResultOfSomethingChecking() {
        this.errors = "";
    }

    public ResultOfSomethingChecking(Subject subject, Theme theme, Question question, User user, ResponseEntity<?> responseEntity, Boolean itsOK, String errors) {
        this.subject = subject;
        this.theme = theme;
        this.question = question;
        this.user = user;
        this.responseEntity = responseEntity;
        this.itsOK = itsOK;
        this.errors = errors;
    }

    public String getErrors() {
        return errors;
    }

    public void setErrors(String errors) {
        this.errors = errors;
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
