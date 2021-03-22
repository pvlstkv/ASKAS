package com.example.javaserver.user.service;

import com.example.javaserver.common_data.model.StudyGroup;
import com.example.javaserver.common_data.repo.StudyGroupRepo;
import com.example.javaserver.general.model.Message;
import com.example.javaserver.general.model.UserContext;
import com.example.javaserver.user.client_model.UserI;
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
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    public ResponseEntity<?> getListUser( ){
        List<User> userList = userRepo.findAll();
        return new ResponseEntity<>(userList,HttpStatus.OK);
    }

    public ResponseEntity<?> updateUser(
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
                    return new ResponseEntity<>(new Message("Такой группы не существует"),HttpStatus.BAD_REQUEST);
                }
            }
            try {
                userRepo.save(user.get());
                return new ResponseEntity<>(new Message("информация о пользователи изменена"),HttpStatus.OK);
            }catch (Exception e){
                e.printStackTrace();
                return new ResponseEntity<>(new Message("ошибка сервера"),HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }else {
            return new ResponseEntity<>(new Message("ошибка при попытке получить информацию о пользователе"), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> deleteUser(Integer id){
        Optional<User> user =  userRepo.findById(id);
        if(user.isPresent()){
            userRepo.delete(user.get());
            return new ResponseEntity<>(new Message("Пользователь удален"), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new Message("ошибка такой пользователь не найден"), HttpStatus.BAD_REQUEST);
        }
    }

}
