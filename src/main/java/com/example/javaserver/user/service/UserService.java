package com.example.javaserver.user.service;

import com.example.javaserver.general.model.Message;
import com.example.javaserver.general.config.JwtUtil;
import com.example.javaserver.general.service.RequestHandlerService;
import com.example.javaserver.user.controller.model.Token;
import com.example.javaserver.user.model.User;
import com.example.javaserver.user.repo.UserRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepo userRepo;
    private final RequestHandlerService requestHandlerService;
    private final JwtUtil jwtUtil;

    public UserService(UserRepo userRepo, RequestHandlerService requestHandlerService, JwtUtil jwtUtil) {
        this.userRepo = userRepo;
        this.requestHandlerService = requestHandlerService;
        this.jwtUtil = jwtUtil;
    }

    public ResponseEntity<?> logUser(User user){
        if(user.getLogin() == null){
            return new ResponseEntity<>(new Message("Введите логин"),HttpStatus.BAD_REQUEST);
        }
        if(user.getPassword() == null){
            return new ResponseEntity<>(new Message("Введите пароль"),HttpStatus.BAD_REQUEST);
        }
        if(!userRepo.existsByLogin(user.getLogin())){
            return new ResponseEntity<>(new Message("Пользователя с таким логином не существует"),HttpStatus.BAD_REQUEST);
        }
        User authUser = userRepo.getUserByLogin(user.getLogin());
        if(authUser.getPassword().equals(user.getPassword())){
            String token = jwtUtil.generateToken(authUser);
            return new ResponseEntity<>(new Token(token),HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(new Message("Неверный пароль"),HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> regUser(User user){
        if(user.getLogin() == null){
            return new ResponseEntity<>(new Message("Введите логин"), HttpStatus.BAD_REQUEST);
        }
        if(user.getPassword() == null){
            return new ResponseEntity<>(new Message("Введите пароль"),HttpStatus.BAD_REQUEST);
        }
        if(user.getEmail() == null){
            return new ResponseEntity<>(new Message("Введите вашу почту"),HttpStatus.BAD_REQUEST);
        }
        if (user.getFirstName() == null){

        }
        if(user.getLastName() == null){

        }
        if(user.getPatronymic() == null){

        }
        if(user.getPhone() == null){

        }
        if(user.getStudyGroup() == null){

        }
        if(userRepo.existsByLogin(user.getLogin())){
            return new ResponseEntity<>(new Message("Имя пользователя занято"),HttpStatus.BAD_REQUEST);
        }
        try {
            userRepo.save(user);
            return new ResponseEntity<>(new Message("Пользователь успешно создан"),HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(new Message("Ошибка попробуйте позже"),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
