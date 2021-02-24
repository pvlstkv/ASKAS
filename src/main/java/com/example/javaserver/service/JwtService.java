package com.example.javaserver.service;

import com.example.javaserver.basemodel.ContextUser;
import com.example.javaserver.config.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    @Autowired
    private JwtUtil jwtUtil;

    public ContextUser modelAuth(String token){
        ContextUser contextUser = new ContextUser();
        contextUser.setIdUser(jwtUtil.extractUserId(token));
        contextUser.setRoleUser(jwtUtil.extractUserRole(token));
        return contextUser;
    }

    public boolean isAdminOrTeacher(ContextUser contextUser) {
        return isAdmin(contextUser) || isTeacher(contextUser);
    }

    public boolean isAdminOrTeacherOrStudent(ContextUser contextUser) {
        return isAdminOrTeacher(contextUser) || isStudent(contextUser);
    }

    public boolean isAdmin(ContextUser contextUser) {
        return contextUser.getRoleUser().equals("admin");
    }

    public boolean isTeacher(ContextUser contextUser) {
        return contextUser.getRoleUser().equals("teacher");
    }

    public boolean isStudent(ContextUser contextUser) {
        return contextUser.getRoleUser().equals("student");
    }

}
