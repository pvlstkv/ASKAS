package com.example.javaserver.user.service;

import com.example.javaserver.common_data.model.StudyGroup;
import com.example.javaserver.common_data.repo.StudyGroupRepo;
import com.example.javaserver.general.model.Message;
import com.example.javaserver.general.model.UserContext;
import com.example.javaserver.user.client_model.UserIO;
import com.example.javaserver.user.model.User;
import com.example.javaserver.user.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
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

    public ResponseEntity<?> getUser(UserContext userContext, Integer id){
        Optional<User> user;
        if(id != null){
           user = userRepo.findById(id);
        }else {
            user = userRepo.findById(userContext.getUserId());
        }
        if(!user.isPresent()){
            return new ResponseEntity<>(new Message("ошибка при попытке получить информацию о пользователе"), HttpStatus.NOT_FOUND);
        }
        UserIO userIO = new UserIO(user.get());
        return new ResponseEntity<>(userIO, HttpStatus.OK);
    }

    public ResponseEntity<?> getListUser( ){
        List<User> userList = userRepo.findAll();
        List<UserIO> userOList = userList.stream().map(user -> {
            UserIO userIO =  new UserIO(user);
            return userIO;
        }).collect(Collectors.toList());
        return new ResponseEntity<>(userOList,HttpStatus.OK);
    }

    public ResponseEntity<?> updateUser(Integer id,
                                        String login,
                                        String password,
                                        String firstName,
                                        String lastName,
                                        String patronymic,
                                        String phone,
                                        String studyGroupName,
                                        String role)
    {
        Optional<User> user = userRepo.findById(id);
        if(user.isPresent()){
            if(login != null){
                user.get().setLogin(login);
            }
            if(password != null){
                user.get().setPassword(password);
            }
            if(firstName != null){
                user.get().setFirstName(firstName);
            }
            if(lastName != null){
                user.get().setLastName(lastName);
            }
            if(password != null){
                user.get().setPatronymic(patronymic);
            }
            if(phone != null){
                user.get().setPhone(phone);
            }
            if(studyGroupName != null){
                if(studyGroupName.equals("null")){
                    user.get().setStudyGroup(null);
                }else {
                    if(studyGroupRepo.existsByShortName(studyGroupName)){
                        Optional<StudyGroup> studyGroup = studyGroupRepo.findByShortName(studyGroupName);
                        user.get().setStudyGroup(studyGroup.get());
                    }else {
                        return new ResponseEntity<>(new Message("Такой группы не существует"),HttpStatus.BAD_REQUEST);
                    }
                }
            }
            try {
                userRepo.save(user.get());
                return new ResponseEntity<>(new Message("информация о пользователи измнена"),HttpStatus.OK);
            }catch (Exception e){
                e.printStackTrace();
                return new ResponseEntity<>(new Message("ошибка сервера"),HttpStatus.INTERNAL_SERVER_ERROR);
            }



        }else {
            return new ResponseEntity<>(new Message("ошибка при попытке получить информацию о пользователе"), HttpStatus.BAD_REQUEST);
        }

    }

    @Transactional
    public ResponseEntity<?> deleteUser(Integer id){
        Optional<User> user =  userRepo.findById(id);
        if(user.isPresent()){
            try {
                userRepo.deleteById(id);
                return new ResponseEntity<>(new Message("Пользователь удален"), HttpStatus.OK);
            }catch (Exception e){
                e.printStackTrace();
                return new ResponseEntity<>(new Message("ошибка сервера"),HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }else {
            return new ResponseEntity<>(new Message("ошибка такой пользователь не найден"), HttpStatus.BAD_REQUEST);
        }
    }

}
