package com.example.javaserver.user_file.repo;

import com.example.javaserver.user_file.model.UserFile;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserFileRepo extends CrudRepository<UserFile, Long> {
    Optional<UserFile> findUserFileByNameEquals(String name);
}
