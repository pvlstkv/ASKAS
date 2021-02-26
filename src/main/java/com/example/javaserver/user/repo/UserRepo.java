package com.example.javaserver.user.repo;

import com.example.javaserver.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface UserRepo extends JpaRepository<User,Integer> {
    boolean existsByLogin(String login);
    User getUserByLogin(String login);
    Set<User> getUsersByIdIn(Set<Integer> ids);
}
