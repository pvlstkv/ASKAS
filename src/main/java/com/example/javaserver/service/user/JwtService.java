package com.example.javaserver.service.user;

import com.example.javaserver.basemodel.ContextUser;
import com.example.javaserver.config.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    @Autowired
    private JwtUtil jwtUtil;

    public ContextUser modelAuth(String token){
        //token = token.substring(7);
        ContextUser contextUser = new ContextUser();
        contextUser.setIdUser(jwtUtil.extractUserId(token));
        contextUser.setRoleUser(jwtUtil.extractUserRole(token));
        return contextUser;
    }

}
