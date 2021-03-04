package com.example.javaserver.user.repo;

import com.example.javaserver.common_data.model.StudyGroup;
import com.example.javaserver.user.model.User;
import com.example.javaserver.user.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface UserRepo extends JpaRepository<User,Integer> {
    boolean existsByLogin(String login);
    User getUserByLogin(String login);
    Set<User> getUsersByIdIn(Set<Integer> id);
    Set<User> getUsersByIdInAndRoleEquals(Set<Integer> id, UserRole userRole);
    //List<User> findByIdIn()
}
