package com.example.javaserver.study.service;

import com.example.javaserver.general.model.Message;
import com.example.javaserver.general.model.UserContext;
import com.example.javaserver.general.model.UserDetailsImp;
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
import org.springframework.web.server.ResponseStatusException;

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
    public UserFileOut create(MultipartFile file, UserRole accessLevel, UserDetailsImp userDetails) {
        Optional<User> user = userRepo.findById(userDetails.getId());
        if (!user.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Токен инвалидный, userId не найден");
        }

        if (accessLevel != null && user.get().getRole().compareTo(accessLevel) < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Уровень доступа к файлу превышает уровень доступа пользователя");
        }

        byte[] data;
        try {
            data = file.getBytes();
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ошибка чтения файла");
        }

        UserFile userFile = new UserFile();
        userFile.setUser(user.get());
        userFile.setAccessLevel(accessLevel == null ? UserRole.USER : accessLevel);
        userFile.setData(data);
        userFile.setName(file.getOriginalFilename());
        userFile = userFileRepo.save(userFile);

        UserFileOut fileOut = new UserFileOut();
        fileOut.id = userFile.getId();

        return fileOut;
    }

    public byte[] download(Long id,UserDetailsImp userDetails) {
        Optional<UserFile> file = userFileRepo.findById(id);
        if (!file.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Файл с указанным id не найден");
        }

        Optional<User> user = userRepo.findById(userDetails.getId());
        if (!user.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Токен инвалидный, userId не найден");
        }

        if (user.get().getRole().compareTo(file.get().getAccessLevel()) < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Отказано в доступе к файлу");
        }

        return file.get().getData();
    }

    public Set<UserFile> getBy(Long[] idsArr) {
        Set<Long> ids = Arrays.stream(idsArr).collect(Collectors.toSet());
        Set<UserFile> files = userFileRepo.getUserFilesByIdIn(ids);

        return files;
    }
}
