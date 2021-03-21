package com.example.javaserver.user_file.repo;

import com.example.javaserver.user_file.model.UserrrrrrrFile;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserFileRepo extends CrudRepository<UserrrrrrrFile, Long> {
    Optional<UserrrrrrrFile> findUserFileByNameEquals(String name);
}
