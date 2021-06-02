package com.example.javaserver.user.service;

import com.example.javaserver.user.model.UserContact;
import com.example.javaserver.user.repo.UserContactRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional
@Service
public class UserContactService {
    private final UserContactRepo userContactRepo;

    @Autowired
    public UserContactService(UserContactRepo userContactRepo) {
        this.userContactRepo = userContactRepo;
    }

    public UserContact getById(Long id) {
        Optional<UserContact> userContactO = userContactRepo.findById(id);
        if (userContactO.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Контакты пользователя с id = " + id + " не найдены");
        }
        return userContactO.get();
    }

    public Set<UserContact> getByIds(Set<Long> ids) {
        Set<UserContact> userContacts = userContactRepo.findAllByIdIn(ids);
        if (userContacts.size() == ids.size()) {
            return userContacts;
        } else {
            Collection<Long> foundIds = userContacts.stream()
                    .map(UserContact::getId)
                    .collect(Collectors.toSet());
            Collection<Long> notFoundIds = ids.stream()
                    .filter(i -> !foundIds.contains(i))
                    .collect(Collectors.toSet());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Контакты пользователей с id: " + Arrays.toString(notFoundIds.toArray()) + " не существуют");
        }
    }

    public UserContact create(UserContact userContact) {
        return userContactRepo.save(userContact);
    }

    public Set<UserContact> delete(Set<Long> ids) {
        return userContactRepo.deleteAllByIdIn(ids);
    }

    public Set<UserContact> getByUserId(Integer userId) {
        return userContactRepo.findAllByUserId(userId);
    }
}
