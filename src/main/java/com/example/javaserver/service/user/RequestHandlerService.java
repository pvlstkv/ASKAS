package com.example.javaserver.service.user;

import com.example.javaserver.basemodel.UserContext;
import com.example.javaserver.basemodel.Message;
import com.example.javaserver.config.JwtUtil;
import com.example.javaserver.model.UserRole;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.EnumSet;
import java.util.function.Function;

@Service
public class RequestHandlerService {
    private final JwtUtil jwtUtil;
    private final JwtService jwtService;

    public RequestHandlerService(JwtUtil jwtUtil, JwtService jwtService) {
        this.jwtUtil = jwtUtil;
        this.jwtService = jwtService;
    }

    public ResponseEntity<?> proceed(String token,
                                     Function<UserContext, ResponseEntity<?>> supplier,
                                     EnumSet<UserRole> setRole){
        try {
            UserContext user = jwtService.modelAuth(token);
            if(setRole.contains(UserRole.valueOf(user.getUserRole()))){
                return supplier.apply(user);
            } else {
                return new ResponseEntity<>(new Message("У вас нет прав к данному ресурсу"),HttpStatus.NOT_FOUND);
            }
        } catch (ExpiredJwtException e){
            return new ResponseEntity<>(HttpStatus.UPGRADE_REQUIRED);
        } catch (MalformedJwtException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
