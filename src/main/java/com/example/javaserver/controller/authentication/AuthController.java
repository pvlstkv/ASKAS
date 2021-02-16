package com.example.javaserver.controller.authentication;

import com.example.javaserver.controller.authentication.model.Token;
import com.example.javaserver.basemodel.Message;
import com.example.javaserver.model.User;
import com.example.javaserver.repo.UserRepo;
import com.example.javaserver.config.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @Autowired
    private UserRepo userRepo;

    private final JwtUtil jwtUtil;

    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/hello")
    public ResponseEntity<?> hi(@RequestHeader (name="Authorization") String token){

        return new ResponseEntity<>(new Message("Привет Мир"),HttpStatus.OK);
    }



    @PostMapping("/registration")
    public ResponseEntity<?> regUser(@RequestBody User user){
        if(user.getLogin() == null){
            return new ResponseEntity<>(new Message("Введите логин"),HttpStatus.BAD_REQUEST);
        }
        if(user.getPassword() == null){
            return new ResponseEntity<>(new Message("Введите пароль"),HttpStatus.BAD_REQUEST);
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

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user){
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



}
