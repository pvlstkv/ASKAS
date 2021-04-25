package com.example.javaserver.testing.n.controller;

import com.example.javaserver.testing.n.dto.answer.for_test.checking.CheckTestDto;
import com.example.javaserver.testing.n.dto.question.QuestionDataDto;
import com.example.javaserver.testing.n.service.TestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public Set<QuestionDataDto> makeTest(
            @RequestParam(value = "theme_id") Long themeId,
            @RequestParam(value = "limit", required = false) Integer countOfQuestions
//            ,
//            @AuthenticationPrincipal UserDetailsImp userDetails
    ) {
        return testService.createTest(themeId, countOfQuestions);
    }

    @PostMapping("/test/check")
    @ResponseStatus(HttpStatus.OK)
//    @Secured({"USER", "TEACHER", "ADMIN"})
    public void checkTest(
            @RequestBody List<CheckTestDto> questions
//            ,
//            @AuthenticationPrincipal UserDetailsImp userDetails
    ) {
       System.out.println(questions);
    }
}
