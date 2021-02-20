package com.example.javaserver.service.user;

import com.example.javaserver.basemodel.UserContext;
import com.example.javaserver.config.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    @Autowired
    private JwtUtil jwtUtil;

    public UserContext modelAuth(String token){
        //token = token.substring(7);
        UserContext userContext = new UserContext();
        userContext.setIdUser(jwtUtil.extractUserId(token));
        userContext.setRoleUser(jwtUtil.extractUserRole(token));
        return userContext;
    }

}
