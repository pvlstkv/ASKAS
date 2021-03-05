package com.example.javaserver.user.service;

import com.example.javaserver.common_data.model.Department;
import com.example.javaserver.common_data.model.StudyGroup;
import com.example.javaserver.common_data.repo.DepartmentRepo;
import com.example.javaserver.common_data.repo.StudyGroupRepo;
import com.example.javaserver.general.model.Message;
import com.example.javaserver.general.config.JwtUtil;
import com.example.javaserver.user.client_model.TokenIO;
import com.example.javaserver.user.client_model.UserI;
import com.example.javaserver.user.model.User;
import com.example.javaserver.user.model.UserRole;
import com.example.javaserver.user.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final UserRepo userRepo;
    private final JwtUtil jwtUtil;
    private final StudyGroupRepo studyGroupRepo;
    private final DepartmentRepo departmentRepo;

    @Autowired
    public AuthService(UserRepo userRepo, JwtUtil jwtUtil, StudyGroupRepo studyGroupRepo, DepartmentRepo departmentRepo) {
        this.userRepo = userRepo;
        this.jwtUtil = jwtUtil;
        this.studyGroupRepo = studyGroupRepo;
        this.departmentRepo = departmentRepo;
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
            return new ResponseEntity<>(new TokenIO(token),HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(new Message("Неверный пароль"),HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> regUser(UserI userI){
        if(userI.getLogin() == null){
            return new ResponseEntity<>(new Message("Введите логин"), HttpStatus.BAD_REQUEST);
        }
        if(userI.getPassword() == null){
            return new ResponseEntity<>(new Message("Введите пароль"),HttpStatus.BAD_REQUEST);
        }
        if(userI.getEmail() == null){
            return new ResponseEntity<>(new Message("Введите вашу почту"),HttpStatus.BAD_REQUEST);
        }
        if (userI.getFirstName() == null){
            return new ResponseEntity<>(new Message("Введите ваше Имя"),HttpStatus.BAD_REQUEST);
        }
        if(userI.getLastName() == null){
            return new ResponseEntity<>(new Message("Введите вашу фамилию"),HttpStatus.BAD_REQUEST);
        }
        /*if(userI.getPatronymic() == null){
            return new ResponseEntity<>(new Message("Введите ваше отчество"),HttpStatus.BAD_REQUEST);
        }*/
        if(userI.getPhone() == null){
            return new ResponseEntity<>(new Message("Введите ваш телефон"),HttpStatus.BAD_REQUEST);
        }
        if(userI.getRole() == null){
            return new ResponseEntity<>(new Message("Укажите роль"),HttpStatus.BAD_REQUEST);
        }

        if(userRepo.existsByLogin(userI.getLogin())){
            return new ResponseEntity<>(new Message("Пользователя с таким логином уже существует"),HttpStatus.CONFLICT);
        }

        if(userI.getRole().equals(UserRole.ADMIN)){
            User user = new User(userI);
            userRepo.save(user);
            return new ResponseEntity<>(new Message("Пользователь с ролью: админ успешно создан"),HttpStatus.CREATED);

        }else if(userI.getRole().equals(UserRole.TEACHER)){
            User user = new User(userI);
            Optional<Department> department = departmentRepo.findByShortName(userI.getDepartmentName());
            if(department.isPresent()){
                user.setDepartment(department.get());
            }else {
                return new ResponseEntity<>(new Message("Такой кафедры не существует"),HttpStatus.BAD_REQUEST);
            }
            userRepo.save(user);
            return new ResponseEntity<>(new Message("Пользователь с ролью: преподаватель успешно создан"),HttpStatus.CREATED);
        }else if(userI.getRole().equals(UserRole.USER)){
            User user = new User(userI);
            Optional<StudyGroup> studyGroup = studyGroupRepo.findById(userI.getStudyGroupId());
            if(studyGroup.isPresent()){
                user.setStudyGroup(studyGroup.get());
            }else {
                return new ResponseEntity<>(new Message("Такой группы не существует"),HttpStatus.BAD_REQUEST);
            }
            try {
                userRepo.save(user);
                return new ResponseEntity<>(new Message("Пользователь с ролью: студент успешно создан"),HttpStatus.CREATED);
            } catch (Exception e){
                return new ResponseEntity<>(new Message("Ошибка попробуйте позже"),HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }else {
            return new ResponseEntity<>(new Message("Ошибка, такой роли не существует"),HttpStatus.BAD_REQUEST);
        }
    }

}
