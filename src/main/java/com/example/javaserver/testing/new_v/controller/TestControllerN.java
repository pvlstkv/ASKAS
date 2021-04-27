package com.example.javaserver.testing.new_v.controller;

import com.example.javaserver.general.model.UserDetailsImp;
import com.example.javaserver.testing.new_v.dto.answer.for_test.checking.CheckTestDto;
import com.example.javaserver.testing.new_v.dto.answer.for_test.result.AfterCheckTestDto;
import com.example.javaserver.testing.new_v.service.TestServiceN;
import com.example.javaserver.testing.new_v.dto.question.QuestionDataDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
public class TestControllerN {

    private final TestServiceN testServiceN;

    @Autowired
    public TestControllerN(TestServiceN testServiceN) {
        this.testServiceN = testServiceN;
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
        return testServiceN.createTest(themeId, countOfQuestions);
    }

    @PostMapping("/test/check")
    @ResponseStatus(HttpStatus.OK)
//    @Secured({"USER", "TEACHER", "ADMIN"})
    public AfterCheckTestDto checkTest(
            @RequestBody List<CheckTestDto> questions
            , @AuthenticationPrincipal UserDetailsImp userDetails
    ) {
        return testServiceN.checkTest(questions, userDetails);
    }
}
