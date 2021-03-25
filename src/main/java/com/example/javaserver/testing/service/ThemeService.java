package com.example.javaserver.testing.service;

import com.example.javaserver.common_data.model.Subject;
import com.example.javaserver.common_data.repo.SubjectRepo;
import com.example.javaserver.general.model.Message;
import com.example.javaserver.general.model.UserContext;
import com.example.javaserver.testing.model.Theme;
import com.example.javaserver.testing.model.dto.ThemeIn;
import com.example.javaserver.testing.repo.ThemeRepo;
import com.example.javaserver.testing.service.model.ResultOfSomethingChecking;
import com.example.javaserver.user.model.User;
import com.example.javaserver.user.repo.UserRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThemeService {
    private final ThemeRepo themeRepo;
    private final SubjectRepo subjectRepo;
    private final UserRepo userRepo;
    private final String doesntExistById = " с id %d в базе данных не существует. " +
            "Пожалуйста проверьте корретность введенных данных.";

    public ThemeService(ThemeRepo themeRepo, SubjectRepo subjectRepo, UserRepo userRepo) {
        this.themeRepo = themeRepo;
        this.subjectRepo = subjectRepo;
        this.userRepo = userRepo;
    }

    public ResponseEntity<?> createTheme(ThemeIn themeIn) {
        ResultOfSomethingChecking checkResult;
        if (themeRepo.existsByName(themeIn.getName())) {
            return new ResponseEntity<>(new Message("Такой предмет уже сущетвует."), HttpStatus.CONFLICT);
        }
        if (!subjectRepo.existsById(themeIn.getSubjectId())) {
            String response = String.format("Предмета" + doesntExistById, themeIn.getSubjectId());
            return new ResponseEntity<>(new Message(response), HttpStatus.BAD_REQUEST);
        }
        Subject subject = subjectRepo.findById(themeIn.getSubjectId()).get();
        Theme theme = new Theme(themeIn, subject);
        themeRepo.save(theme);
        return new ResponseEntity<>(HttpStatus.CREATED);
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

//    public ResponseEntity<?> fetchPassedThemes(UserContext userContext) {
//        if (!userRepo.existsById(userContext.getUserId())) {
//            String response = String.format("Пользователя" + doesntExistById, userContext.getUserId());
//            return new ResponseEntity<>(new Message(response), HttpStatus.UNPROCESSABLE_ENTITY);
//        }
//        List<Theme> themes =
//    }
}

