package com.example.javaserver.testing.theme;

import com.example.javaserver.common_data.model.Subject;
import com.example.javaserver.common_data.repo.SubjectRepo;


import com.example.javaserver.testing.theme.dto.theme.ThemeIn;
import com.example.javaserver.testing.theme.dto.theme.ThemeUpdateIn;
import com.example.javaserver.user.repo.UserRepo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    public Long createTheme(ThemeIn themeIn) {
//        if (themeRepo.existsByName(themeIn.getName())) {
//            throw new ResponseStatusException(HttpStatus.CONFLICT, "Такая тема уже сущетвует.");
//        }
        if (!subjectRepo.existsById(themeIn.getSubjectId())) {
            String response = String.format("Предмета" + doesntExistById, themeIn.getSubjectId());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, response);
        }
        Subject subject = subjectRepo.findById(themeIn.getSubjectId()).get();
        Theme theme = new Theme(themeIn, subject);
        theme = themeRepo.save(theme);
        themeRepo.flush();
        return theme.getId();
    }

    public void updateTheme(ThemeUpdateIn themeUpdateIn) {
        if (!subjectRepo.existsById(themeUpdateIn.getSubjectId())) {
            String response = String.format("Предмета" + doesntExistById, themeUpdateIn.getSubjectId());
            throw new ResponseStatusException(HttpStatus.CONFLICT, response);
        }
        Subject subject = subjectRepo.findById(themeUpdateIn.getSubjectId()).get();
        if (themeRepo.existsById(themeUpdateIn.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Такая тема уже сущетвует.");
        }
        Theme newTheme = new Theme();
    }

    public List<Theme> fetchSubjectThemes(Long subjectId) {
        Subject tempSubject = (subjectRepo.findById(subjectId)
                .orElse(null));
        if (tempSubject == null) {
            String response = String.format("Предмета" + doesntExistById, subjectId);
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, response);
        }
        return themeRepo.findAllBySubjectId(subjectId);
    }

    public void deleteThemes(List<Long> ids) {
        ids.forEach(themeRepo::deleteById);
    }

//    public ResponseEntity<?> fetchPassedThemes(UserContext userContext) {
//        if (!userRepo.existsById(userContext.getUserId())) {
//            String response = String.format("Пользователя" + doesntExistById, userContext.getUserId());
//            return new ResponseEntity<>(new Message(response), HttpStatus.UNPROCESSABLE_ENTITY);
//        }
//        List<Theme> themes =
//    }
}

