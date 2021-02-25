package com.example.javaserver.repo;

import com.example.javaserver.model.user_file.UserFile;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserFileRepo extends CrudRepository<UserFile, Long> {
    Optional<UserFile> findUserFileByNameEquals(String name);
}
