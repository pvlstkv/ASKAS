package com.example.javaserver.testing.n.controller;

import com.example.javaserver.testing.n.model.QuestionData;
import com.example.javaserver.testing.n.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class TestController {

    private final TestService testService;

    @Autowired
    public TestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping("/test")
    @ResponseStatus(HttpStatus.OK)
//    @Secured({"USER", "TEACHER", "ADMIN"})
    public Set<QuestionData> makeTest(
            @RequestParam(value = "theme_id") Long themeId,
            @RequestParam(value = "limit", required = false) Integer countOfQuestions
//            ,
//            @AuthenticationPrincipal UserDetailsImp userDetails
    ) {
        return testService.createTest(themeId, countOfQuestions);
    }
}
