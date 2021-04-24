package com.example.javaserver.user.service;

import com.example.javaserver.user.model.User;
import com.example.javaserver.user.model.UserRole;
import com.example.javaserver.user.repo.UserRepo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.Set;

@Service
public class UserDataService {
    private final UserRepo userRepo;

    public UserDataService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public Set<User> getUsersByIdIn(Set<Integer> ids){
        return userRepo.getUsersByIdIn(ids);
    }

    public User getById(Integer id){
        Optional<User> userO = userRepo.findById(id);
        if (userO.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Нет пользователя с указанным (явно или по токену) id");
        }
        return userO.get();
    }

    public Set<User> getUsersByIdInAndRoleEquals(Set<Integer> ids,UserRole userRole){
        return userRepo.getUsersByIdInAndRoleEquals(ids, userRole);
    }


}
