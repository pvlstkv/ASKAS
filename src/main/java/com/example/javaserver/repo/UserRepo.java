package com.example.javaserver.repo;

import com.example.javaserver.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepo extends JpaRepository<User,Integer> {
    boolean existsByLogin(String login);
    User getUserByLogin(String login);
}
