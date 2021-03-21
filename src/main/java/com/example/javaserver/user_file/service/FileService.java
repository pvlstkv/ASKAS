package com.example.javaserver.user_file.service;

import com.example.javaserver.common_data.model.SubjectSemester;
import com.example.javaserver.common_data.repo.SubjectSemesterRepo;
import com.example.javaserver.general.model.UserContext;
import com.example.javaserver.general.model.Message;
import com.example.javaserver.user.model.User;
import com.example.javaserver.user_file.model.UserrrrrrrFile;
import com.example.javaserver.user.model.UserRole;
import com.example.javaserver.user_file.repo.UserFileRepo;
import com.example.javaserver.user.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.EnumSet;
import java.util.Optional;

@Service
public class FileService {
    private final EnumSet<UserRole> rolesCanReadAnyFiles =
            EnumSet.of(UserRole.TEACHER, UserRole.ADMIN);

    private final UserRepo userRepo;
    private final SubjectSemesterRepo subjectSemesterRepo;
    private final UserFileRepo userFileRepo;

    @Autowired
    public FileService(UserRepo userRepo, SubjectSemesterRepo subjectSemesterRepo, UserFileRepo userFileRepo) {
        this.userRepo = userRepo;
        this.subjectSemesterRepo = subjectSemesterRepo;
        this.userFileRepo = userFileRepo;
    }

    @Transactional
    public ResponseEntity<?> uploadFile(
            UserContext userContext,
            String name,
            Long subjectSemesterId,
            MultipartFile file
    ) {
        Optional<SubjectSemester> semester = subjectSemesterRepo.findById(subjectSemesterId);
        if (!semester.isPresent()) {
            return new ResponseEntity<>(new Message("Семестр с указанным id не существует"), HttpStatus.BAD_REQUEST);
        }

        byte[] data;
        try {
            data = file.getBytes();
        } catch (IOException e) {
            return new ResponseEntity<>(new Message("Ошибка чтения файла"), HttpStatus.BAD_REQUEST);
        }

        Integer userId = userContext.getUserId();
        User user = userRepo.getOne(userId);
        UserrrrrrrFile userrrrrrrFile = new UserrrrrrrFile(name, user, semester.get(), data);
        try {
            userFileRepo.save(userrrrrrrFile);
        } catch (Exception e) {
            return new ResponseEntity<>(new Message("Ошибка записи файла"), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(new Message("Файл успешно загружен"), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> downloadFile(
            UserContext userContext,
            String name
    ) {
        Optional<UserrrrrrrFile> fileOpt = userFileRepo.findUserFileByNameEquals(name);
        if (!fileOpt.isPresent()) {
            return new ResponseEntity<>(new Message("Файл с таким именем не найден"), HttpStatus.BAD_REQUEST);
        }

        Integer userId = userContext.getUserId();
        User requester = userRepo.getOne(userId);
        UserrrrrrrFile file = fileOpt.get();
        User fileOwner = file.getUser();
        if ((!fileOwner.getId().equals(userId))
                && !(rolesCanReadAnyFiles.contains(requester.getRole()))) {
            return new ResponseEntity<>(new Message("Отказано в доступе к файлу"), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(file.getData(), HttpStatus.OK);
    }
}
