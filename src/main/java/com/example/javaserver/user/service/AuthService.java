package com.example.javaserver.user.service;

import com.example.javaserver.common_data.model.Department;
import com.example.javaserver.common_data.model.StudyGroup;
import com.example.javaserver.common_data.repo.DepartmentRepo;
import com.example.javaserver.common_data.repo.StudyGroupRepo;
import com.example.javaserver.general.model.Message;
import com.example.javaserver.general.config.JwtUtil;
import com.example.javaserver.user.controller.dto.TokenIO;
import com.example.javaserver.user.controller.dto.UserI;
import com.example.javaserver.user.model.User;
import com.example.javaserver.user.model.UserRole;
import com.example.javaserver.user.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
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

    @Transactional
    public TokenIO logUser(User user){
        if(user.getLogin() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Введите логин");
        }
        if(user.getPassword() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Введите пароль");
        }
        if(!userRepo.existsByLogin(user.getLogin())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Пользователя с таким логином не существует");
        }
        User authUser = userRepo.getUserByLogin(user.getLogin());
        if(authUser.getPassword().equals(user.getPassword())){
            String token = jwtUtil.generateToken(authUser);
            return new TokenIO(token);
        }
        else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Неверный пароль");
        }
    }

    @Transactional
    public Message regUser(UserI userI){
        if(userI.getLogin() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Введите логин");
        }
        if(userI.getPassword() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Введите пароль");
        }
        if(userI.getEmail() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Введите вашу почту");
        }
        if (userI.getFirstName() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Введите ваше Имя");
        }
        if(userI.getLastName() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Введите вашу фамилию");
        }
        /*if(userI.getPatronymic() == null){
            return new ResponseEntity<>(new Message("Введите ваше отчество"),HttpStatus.BAD_REQUEST);
        }*/
        if(userI.getPhone() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Введите ваш телефон");
        }
        if(userI.getRole() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Укажите роль");
        }

        if(userRepo.existsByLogin(userI.getLogin())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Пользователя с таким логином уже существует");
        }

        if(userI.getRole().equals(UserRole.ADMIN)){
            User user = new User(userI);
            userRepo.save(user);
            return new Message("Пользователь с ролью: админ успешно создан");

        }else if(userI.getRole().equals(UserRole.TEACHER)){
            User user = new User(userI);
            Optional<Department> department = departmentRepo.findByShortName(userI.getDepartmentName());
            if(department.isPresent()){
                user.setDepartment(department.get());
            }else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Такой кафедры не существует");
            }
            userRepo.save(user);
            return new Message("Пользователь с ролью: преподаватель успешно создан");
        }else if(userI.getRole().equals(UserRole.USER)){
            User user = new User(userI);
            Optional<StudyGroup> studyGroup = studyGroupRepo.findById(userI.getStudyGroupId());
            if(studyGroup.isPresent()){
                user.setStudyGroup(studyGroup.get());
            }else {
                return new Message("Такой группы не существует");
            }
            try {
                userRepo.save(user);
                return new Message("Пользователь с ролью: студент успешно создан");
            } catch (Exception e){
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ошибка попробуйте позже");
            }
        }else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ошибка, такой роли не существует");
        }
    }

}
