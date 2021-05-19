package com.example.javaserver.testing.new_v.service.model;

import com.example.javaserver.common_data.model.Subject;
import com.example.javaserver.common_data.repo.SubjectRepo;
import com.example.javaserver.testing.new_v.model.question.QuestionData;
import com.example.javaserver.testing.new_v.repo.question.QuestionDataRepo;
import com.example.javaserver.testing.theme.Theme;
import com.example.javaserver.testing.theme.ThemeRepo;
import com.example.javaserver.user.model.User;
import com.example.javaserver.user.repo.UserRepo;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


public class ResultOfSomethingChecking {
    private Subject subject;
    private Theme theme;
    private QuestionData question;
    private User user;
    private Boolean itsOK;
    private String errors;
    private static String doesntExistById = " с id %d в базе данных не существует. " +
            "Пожалуйста проверьте корретность введенных данных.\n";

    public ResultOfSomethingChecking(ResultOfSomethingChecking oldResult) {
        this.subject = oldResult.getSubject();
        this.theme = oldResult.getTheme();
        this.question = oldResult.getQuestion();
        this.user = oldResult.getUser();
        this.itsOK = oldResult.getItsOK();
        this.errors = oldResult.getErrors();
    }

    public <T, K> ResultOfSomethingChecking checkIfExistsInDB(T something, K repo, ResultOfSomethingChecking prevResult) {
        ResultOfSomethingChecking newResult = new ResultOfSomethingChecking(prevResult);
        if ((something instanceof Subject) && (repo instanceof SubjectRepo)) {
            return checkSubject((Subject) something, (SubjectRepo) repo, newResult);
        }
        if (something instanceof Theme && repo instanceof ThemeRepo) {
            return checkTheme((Theme) something, (ThemeRepo) repo, newResult);
        }
        if (something instanceof QuestionData && repo instanceof QuestionDataRepo) {
            return checkQuestion((QuestionData) something, (QuestionDataRepo) repo, newResult);
        }
        if (something instanceof User && repo instanceof UserRepo) {
            return checkUser((User) something, (UserRepo) repo, newResult);
        }
        newResult.setErrors("хех");
        newResult.setItsOK(false);
        return newResult;
    }

    private static ResultOfSomethingChecking checkSubject(Subject subject, SubjectRepo repo, ResultOfSomethingChecking result) {
        Subject subjectFromDB = repo.findById(subject.getId()).orElse(null);
        if (subjectFromDB == null) {
            String response = String.format("Предмета" + doesntExistById, subject.getId());
            result.errors += response;
            result.setItsOK(false);
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, result.errors);
        }
        result.setItsOK(true);
        result.setSubject(subject);
        return result;
    }

    private static ResultOfSomethingChecking checkTheme(Theme theme, ThemeRepo repo, ResultOfSomethingChecking result) {
        Theme themeFromDB = repo.findById(theme.getId()).orElse(null);
        if (themeFromDB == null) {
            String response = String.format("Темы" + doesntExistById, theme.getId());
            result.errors += response;
            result.setItsOK(false);
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, result.errors);
        }
        result.setItsOK(true);
        result.setTheme(themeFromDB);
        return result;
    }

    private static ResultOfSomethingChecking checkQuestion(QuestionData question, QuestionDataRepo repo, ResultOfSomethingChecking result) {
        QuestionData questionFromDB = repo.findById(question.getId()).orElse(null);
        if (questionFromDB == null) {
            String response = String.format("Вопроса" + doesntExistById, question.getId());
            result.errors += response;
            result.setItsOK(false);
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, result.errors);
        }
        result.setItsOK(true);
        result.setQuestion(questionFromDB);
        return result;
    }

    private static ResultOfSomethingChecking checkUser(User user, UserRepo repo, ResultOfSomethingChecking result) {
        User userFromDB = repo.findById(user.getId()).orElse(null);
        if (userFromDB == null) {
            String response = String.format("Пользователя" + doesntExistById, user.getId());
            result.errors += response;
            result.setItsOK(false);
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, result.errors);
        }
        result.setItsOK(true);
        result.setUser(userFromDB);
        return result;
    }


    public ResultOfSomethingChecking() {
        this.errors = "";
    }

    public ResultOfSomethingChecking(Subject subject, Theme theme, QuestionData question, User user, Boolean itsOK, String errors) {
        this.subject = subject;
        this.theme = theme;
        this.question = question;
        this.user = user;
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

    public QuestionData getQuestion() {
        return question;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setQuestion(QuestionData question) {
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

    public static String getDoesntExistById() {
        return doesntExistById;
    }

    public static void setDoesntExistById(String doesntExistById) {
        ResultOfSomethingChecking.doesntExistById = doesntExistById;
    }
}
