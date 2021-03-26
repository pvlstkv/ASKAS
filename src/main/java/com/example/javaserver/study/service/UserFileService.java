package com.example.javaserver.study.service;

import com.example.javaserver.general.model.Message;
import com.example.javaserver.general.model.UserContext;
import com.example.javaserver.study.controller.dto.UserFileOut;
import com.example.javaserver.study.model.UserFile;
import com.example.javaserver.study.repo.UserFileRepo;
import com.example.javaserver.user.model.User;
import com.example.javaserver.user.model.UserRole;
import com.example.javaserver.user.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserFileService {
    private final UserFileRepo userFileRepo;
    private final UserRepo userRepo;

    @Autowired
    public UserFileService(UserFileRepo userFileRepo, UserRepo userRepo) {
        this.userFileRepo = userFileRepo;
        this.userRepo = userRepo;
    }

    @Transactional
    public ResponseEntity<?> create(MultipartFile file, UserRole accessLevel, UserContext userContext) {
        Optional<User> user = userRepo.findById(userContext.getUserId());
        if (!user.isPresent()) {
            return new ResponseEntity<>(new Message("Токен инвалидный, userId не найден"), HttpStatus.BAD_REQUEST);
        }

        if (accessLevel != null && user.get().getRole().compareTo(accessLevel) < 0) {
            return new ResponseEntity<>(new Message("Уровень доступа к файлу превышает уровень доступа пользователя"), HttpStatus.BAD_REQUEST);
        }

        byte[] data;
        try {
            data = file.getBytes();
        } catch (IOException e) {
            return new ResponseEntity<>(new Message("Ошибка чтения файла"), HttpStatus.BAD_REQUEST);
        }

        UserFile userFile = new UserFile();
        userFile.setUser(user.get());
        userFile.setAccessLevel(accessLevel == null ? UserRole.USER : accessLevel);
        userFile.setData(data);
        userFile.setName(file.getOriginalFilename());
        userFile = userFileRepo.save(userFile);

        UserFileOut fileOut = new UserFileOut();
        fileOut.id = userFile.getId();

        return new ResponseEntity<>(fileOut, HttpStatus.CREATED);
    }

    public ResponseEntity<?> download(Long id, UserContext userContext) {
        Optional<UserFile> file = userFileRepo.findById(id);
        if (!file.isPresent()) {
            return new ResponseEntity<>(new Message("Файл с указанным id не найден"), HttpStatus.BAD_REQUEST);
        }

        Optional<User> user = userRepo.findById(userContext.getUserId());
        if (!user.isPresent()) {
            return new ResponseEntity<>(new Message("Токен инвалидный, userId не найден"), HttpStatus.BAD_REQUEST);
        }

        if (user.get().getRole().compareTo(file.get().getAccessLevel()) < 0) {
            return new ResponseEntity<>(new Message("Отказано в доступе к файлу"), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(file.get().getData(), HttpStatus.OK);
    }

<<<<<<< HEAD
    public ResponseEntity<?> getBy(Long[] idsArr, UserContext userContext) {
        Set<Long> ids = Arrays.stream(idsArr).collect(Collectors.toSet());
        Set<UserFile> files = userFileRepo.getUserFilesByIdIn(ids);

        return new ResponseEntity<>(files, HttpStatus.OK);
    }
=======

>>>>>>> feature/testing-service
}
