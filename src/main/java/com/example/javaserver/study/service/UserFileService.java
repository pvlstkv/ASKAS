package com.example.javaserver.study.service;

import com.example.javaserver.general.model.UserDetailsImp;
import com.example.javaserver.study.model.UserFile;
import com.example.javaserver.study.repo.UserFileRepo;
import com.example.javaserver.user.model.User;
import com.example.javaserver.user.model.UserRole;
import com.example.javaserver.user.repo.UserRepo;
import io.minio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.io.InputStream;
import java.util.Optional;
import java.util.Set;

@Service
public class UserFileService {
    private final MinioClient minioClient;
    private final UserFileRepo userFileRepo;
    private final UserRepo userRepo;
    private final String bucketName;

    @Autowired
    public UserFileService(
            MinioClient minioClient,
            UserFileRepo userFileRepo,
            UserRepo userRepo,
            @Value("${spring.minio.bucket}") String bucketName
    ) {
        this.minioClient = minioClient;
        this.userFileRepo = userFileRepo;
        this.userRepo = userRepo;
        this.bucketName = bucketName;
    }

    @Transactional
    public UserFile upload(MultipartFile multipartFile, UserRole accessLevel, UserDetailsImp userDetails) {
        Optional<User> user = userRepo.findById(userDetails.getId());
        if (!user.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Токен инвалидный, userId не найден");
        }

        if (accessLevel != null && user.get().getRole().compareTo(accessLevel) < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Уровень доступа к файлу превышает уровень доступа пользователя");
        }

        UserFile userFile = new UserFile();
        userFile.setUser(user.get());
        userFile.setAccessLevel(accessLevel == null ? UserRole.USER : accessLevel);
        userFile.setName(multipartFile.getOriginalFilename());
        userFile = userFileRepo.save(userFile);

        try {
            InputStream stream = multipartFile.getInputStream();
            PutObjectArgs args = PutObjectArgs.builder()
                    .bucket(bucketName)
                    .stream(stream, stream.available(), -1L)
                    .object(userFile.getId().toString())
                    .build();
            minioClient.putObject(args);
        } catch (Exception e) {
            userFileRepo.deleteById(userFile.getId());
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ошибка загрузки файла", e);
        }

        return userFile;
    }

    public Resource download(Long id, UserDetailsImp userDetails) {
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

        GetObjectArgs args = GetObjectArgs.builder()
                .bucket(bucketName)
                .object(id.toString())
                .build();

        try {
            GetObjectResponse response = minioClient.getObject(args);
            return new ByteArrayResource(response.readAllBytes());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ошибка скачивания файла", e);
        }
    }

    public Set<UserFile> getBy(Set<Long> ids) {
        return userFileRepo.getUserFilesByIdIn(ids);
    }
}