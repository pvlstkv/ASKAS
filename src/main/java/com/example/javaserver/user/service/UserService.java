package com.example.javaserver.user.service;

import com.example.javaserver.common_data.repo.StudyGroupRepo;
import com.example.javaserver.general.model.Message;
import com.example.javaserver.general.model.UserDetailsImp;
import com.example.javaserver.user.controller.dto.UserI;
import com.example.javaserver.user.controller.dto.UpdateUser;
import com.example.javaserver.user.model.User;
import com.example.javaserver.user.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepo userRepo;
    private final StudyGroupRepo studyGroupRepo;

    @Autowired
    public UserService(UserRepo userRepo, StudyGroupRepo studyGroupRepo) {
        this.userRepo = userRepo;
        this.studyGroupRepo = studyGroupRepo;
    }

    public User getUser(UserDetailsImp userDetails, Integer id){
        Optional<User> user;
        if(id != null){
           user = userRepo.findById(id);
        }else {
            user = userRepo.findById(userDetails.getId());
        }
        if(user.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ошибка при попытке получить информацию о пользователе");
        }
        return user.get();
    }

    public User getById(Integer id) {
        Optional<User> userO = userRepo.findByIdEquals(id);
        if (userO.isEmpty()) {
            throw new EntityNotFoundException("Пользователь с указанным id не существует");
        }
        return userO.get();
    }

    public Set<User> getByIds(Set<Integer> ids) {
        Set<User> users = userRepo.findAllByIdIn(ids);
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

    @Transactional
    public Message putUser(UserDetailsImp userDetails, UpdateUser updateUser){
        Optional<User> user = userRepo.findById(userDetails.getId());
        if(user.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ошибка при попытке получить информацию о пользователе");
        }
        if(updateUser.getLogin() != null)
            user.get().setLogin(updateUser.getLogin());
        if(updateUser.getPassword() != null)
            user.get().setPassword(updateUser.getPassword());
        if(updateUser.getEmail() != null)
            user.get().setEmail(updateUser.getEmail());
        if(updateUser.getPhone() != null)
            user.get().setPhone(updateUser.getPhone());
        userRepo.save(user.get());
        return new Message("Информация обновлена");

    }


    public List<User> getListUser( ){
        return userRepo.findAll();
    }

    public Message updateUser(
          UserI userI
    )
    {
        Optional<User> user = userRepo.findById(userI.getId());
        if(user.isPresent()){
            if(userI.getLogin() != null){
                user.get().setLogin(userI.getLogin());
            }
            if(userI.getPassword() != null){
                user.get().setPassword(userI.getPassword());
            }
            if(userI.getFirstName() != null){
                user.get().setFirstName(userI.getFirstName());
            }
            if(userI.getLastName() != null){
                user.get().setLastName(userI.getLastName());
            }
            if(userI.getPatronymic() != null){
                user.get().setPatronymic(userI.getPatronymic());
            }
            if(userI.getPhone() != null){
                user.get().setPhone(userI.getPhone());
            }
            if(userI.getStudyGroupId() != null){
                if(studyGroupRepo.existsById(userI.getStudyGroupId())){
                    user.get().setStudyGroup(studyGroupRepo.findById(userI.getStudyGroupId()).get());
                }else {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Такой группы не существует");
                }
            }
            try {
                userRepo.save(user.get());
                return new Message("информация о пользователи изменена");
            }catch (Exception e){
                e.printStackTrace();
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ошибка сервера");
            }
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ошибка при попытке получить информацию о пользователе");
        }
    }

    public Message deleteUser(Integer id){
        Optional<User> user =  userRepo.findById(id);
        if(user.isPresent()){
            userRepo.delete(user.get());
            return new Message("Пользователь удален");
        }else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ошибка такой пользователь не найден");
        }
    }

}
