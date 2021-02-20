package com.example.javaserver.repo;

import com.example.javaserver.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Integer> {
    boolean existsByLogin(String login);
    User getUserByLogin(String login);
}
