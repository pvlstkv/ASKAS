package com.example.javaserver.service.user;

import com.example.javaserver.basemodel.UserContext;
import com.example.javaserver.config.JwtUtil;
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
}
