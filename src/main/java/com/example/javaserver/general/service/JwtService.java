package com.example.javaserver.general.service;

import com.example.javaserver.general.model.UserContext;
import com.example.javaserver.general.config.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    private final JwtUtil jwtUtil;

    @Autowired
    public JwtService(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    public UserContext modelAuth(String token){
        //token = token.substring(7);
        UserContext userContext = new UserContext();
        userContext.setUserId(jwtUtil.extractUserId(token));
        userContext.setUserRole(jwtUtil.extractUserRole(token));
        return userContext;
    }


    public boolean isAdminOrTeacher(UserContext userContext) {
        return isAdmin(userContext) || isTeacher(userContext);
    }

    public boolean isAdminOrTeacherOrStudent(UserContext userContext) {
        return isAdminOrTeacher(userContext) || isStudent(userContext);
    }

    public boolean isAdmin(UserContext userContext) {
        return userContext.getUserRole().equals("admin");
    }

    public boolean isTeacher(UserContext userContext) {
        return userContext.getUserRole().equals("teacher");
    }

    public boolean isStudent(UserContext userContext) {
        return userContext.getUserRole().equals("student");
    }
}
