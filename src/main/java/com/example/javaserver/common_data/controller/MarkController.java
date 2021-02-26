package com.example.javaserver.common_data.controller;

import com.example.javaserver.common_data.model.Mark;
import com.example.javaserver.common_data.service.MarkService;
import com.example.javaserver.general.service.RequestHandlerService;
import com.example.javaserver.user.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.EnumSet;

@RestController
@RequestMapping("/mark")
public class MarkController {
    private final RequestHandlerService requestHandlerService;
    private final MarkService markService;

    @Autowired
    public MarkController(RequestHandlerService requestHandlerService, MarkService markService) {
        this.requestHandlerService = requestHandlerService;
        this.markService = markService;
    }

    @PostMapping("/creation")
    public ResponseEntity<?> create(
            @RequestHeader("token") String token,
            @RequestBody Mark mark
    ) {
        return requestHandlerService.proceed(
                token,
                (c) -> markService.create(mark),
                EnumSet.of(UserRole.ADMIN, UserRole.TEACHER)
        );
    }
}
