package com.example.javaserver.testing.controller;


import com.example.javaserver.general.model.Message;
import com.example.javaserver.general.model.UserDetailsImp;
import com.example.javaserver.testing.model.Question;
import com.example.javaserver.testing.model.dto.*;
import com.example.javaserver.testing.service.QuestionService;
import com.example.javaserver.testing.service.ResultService;
import com.example.javaserver.testing.service.TestService;
import com.example.javaserver.testing.service.ThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/testing/")
public class TestingController {


    private final TestService testService;

    @Autowired
    public TestingController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping("/hello")
    @ResponseStatus(HttpStatus.OK)
    public Message hi() {
        return new Message("Привет я работаю");
    }

    @GetMapping("/test")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    public ResponseEntity<?> makeTest(
            @RequestParam(value = "theme_id") Long themeId,
            @RequestParam(value = "limit", required = false) Integer countOfQuestions,
            @AuthenticationPrincipal UserDetailsImp userDetails
    ) {
        return testService.createTest(themeId, countOfQuestions);
    }

    @PostMapping("/test/checking")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    public ResponseEntity<?> checkTest(@RequestBody List<AnswerInOut> userTest,
                                       @AuthenticationPrincipal UserDetailsImp userDetails
    ) {
        return testService.checkTest(userTest, userDetails);
    }


}
