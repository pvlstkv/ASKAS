package com.example.javaserver.service.file;

import com.example.javaserver.basemodel.UserContext;
import com.example.javaserver.basemodel.Message;
import com.example.javaserver.model.User;
import com.example.javaserver.model.user_file.UserFile;
import com.example.javaserver.model.UserRole;
import com.example.javaserver.repo.UserFileRepo;
import com.example.javaserver.repo.UserRepo;
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
    //private final SubjectRepo subjectRepo;
    private final UserFileRepo userFileRepo;

    @Autowired
    public FileService(UserRepo userRepo, /*SubjectRepo subjectRepo,*/ UserFileRepo userFileRepo) {
        //this.subjectRepo = subjectRepo;
        this.userRepo = userRepo;
        this.userFileRepo = userFileRepo;
    }

    @Transactional
    public ResponseEntity<?> uploadFile(
            UserContext userContext,
            String name,
            String subjectName,
            MultipartFile file
    ) {
        /*Optional<Subject> subjectOpt = subjectRepo.findByNameEquals(subjectName);
        if (!subjectOpt.isPresent()) {
            return new ResponseEntity<>(new Message("Предмет с таким именем не найден"), HttpStatus.BAD_REQUEST);
        }*/

        byte[] data;
        try {
            data = file.getBytes();
        } catch (IOException e) {
            return new ResponseEntity<>(new Message("Ошибка чтения файла"), HttpStatus.EXPECTATION_FAILED);
        }

        Integer userId = userContext.getUserId();
        User user = userRepo.getOne(userId);
        UserFile userFile = new UserFile(name, user, /*subjectOpt.get()*/null, data);
        try {
            userFileRepo.save(userFile);
        } catch (Exception e) {
            return new ResponseEntity<>(new Message("Ошибка записи файла"), HttpStatus.EXPECTATION_FAILED);
        }

        return new ResponseEntity<>(new Message("Файл успешно загружен"), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> downloadFile(
            UserContext userContext,
            String name
    ) {
        Optional<UserFile> fileOpt = userFileRepo.findUserFileByNameEquals(name);
        if (!fileOpt.isPresent()) {
            return new ResponseEntity<>(new Message("Файл с таким именем не найден"), HttpStatus.EXPECTATION_FAILED);
        }

        Integer userId = userContext.getUserId();
        User requester = userRepo.getOne(userId);
        UserFile file = fileOpt.get();
        User fileOwner = file.getUser();
        if ((!fileOwner.getId().equals(userId))
                && !(rolesCanReadAnyFiles.contains(requester.getRole()))) {
            return new ResponseEntity<>(new Message("Отказано в доступе к файлу"), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(file.getData(), HttpStatus.OK);
    }
}
