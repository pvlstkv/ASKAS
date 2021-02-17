package com.example.javaserver.service;

import com.example.javaserver.basemodel.ContextUser;
import com.example.javaserver.basemodel.Message;
import com.example.javaserver.config.JwtUtil;
import com.example.javaserver.model.UserRole;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Supplier;

@Service
public class RequestHandlerService {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private JwtService jwtService;

    public ResponseEntity<?> proceed(String token, Supplier<ResponseEntity<?>> supplier, EnumSet<UserRole> setRole){
        try {
            ContextUser user = jwtService.modelAuth(token);
            if(setRole.contains(UserRole.valueOf(user.getRoleUser()))){
                return supplier.get();
            } else {
                return new ResponseEntity<>(new Message("У вас нет прав к данному ресурсу"),HttpStatus.NOT_FOUND);
            }
        } catch (ExpiredJwtException e){
            return new ResponseEntity<>(HttpStatus.UPGRADE_REQUIRED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }




}
