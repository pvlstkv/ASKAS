//package com.example.javaserver.service;
//
//import com.example.javaserver.basic.model.UserContext;
//import com.example.javaserver.basic.model.UserContext;
//import com.example.javaserver.basic.config.JwtUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class JwtService {
//    @Autowired
//    private JwtUtil jwtUtil;
//
//    public UserContext modelAuth(String token){
//        UserContext userContext = new UserContext();
//        userContext.setUserId(jwtUtil.extractUserId(token));
//        userContext.setUserRole(jwtUtil.extractUserRole(token));
//        return userContext;
//    }
//
//    public boolean isAdminOrTeacher(UserContext userContext) {
//        return isAdmin(userContext) || isTeacher(userContext);
//    }
//
//    public boolean isAdminOrTeacherOrStudent(UserContext userContext) {
//        return isAdminOrTeacher(userContext) || isStudent(userContext);
//    }
//
//    public boolean isAdmin(UserContext userContext) {
//        return userContext.getUserRole().equals("admin");
//    }
//
//    public boolean isTeacher(UserContext userContext) {
//        return userContext.getUserRole().equals("teacher");
//    }
//
//    public boolean isStudent(UserContext userContext) {
//        return userContext.getUserRole().equals("student");
//    }
//
//}
