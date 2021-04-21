package com.example.javaserver.user.controller.mapper;

import com.example.javaserver.study.model.Task;
import com.example.javaserver.user.model.User;
import com.example.javaserver.user.repo.UserRepo;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
@Component
public class UserIdMapper {
    private final UserRepo repo;

    @Autowired
    public UserIdMapper(UserRepo repo) {
        this.repo = repo;
    }

    public User getById(Integer id) {
        Optional<User> userO = repo.findByIdEquals(id);
        if (userO.isEmpty()) {
            throw new EntityNotFoundException("Пользователь с указанным id не существует");
        }
        return userO.get();
    }

    public Integer getId(User user) {
        return user == null
                ? null
                : user.getId();
    }

    public Set<User> getByIds(Set<Integer> ids) {
        Set<User> users = repo.findAllByIdIn(ids);
        if (users.size() == ids.size()) {
            return users;
        } else {
            Collection<Integer> foundIds = users.stream()
                    .map(User::getId)
                    .collect(Collectors.toSet());
            Collection<Integer> notFoundIds = ids.stream()
                    .filter(i -> !foundIds.contains(i))
                    .collect(Collectors.toSet());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Пользователи с id: " + Arrays.toString(notFoundIds.toArray()) + " не существуют");
        }
    }

    public Set<Integer> getIds(Set<User> users) {
        return users == null
                ? null
                : users.stream().map(User::getId).collect(Collectors.toSet());
    }
}
