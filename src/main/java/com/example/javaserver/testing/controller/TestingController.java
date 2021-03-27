package com.example.javaserver.testing.controller;


import com.example.javaserver.general.model.Message;
import com.example.javaserver.general.service.RequestHandlerService;
import com.example.javaserver.testing.model.dto.AnswerInOut;
import com.example.javaserver.testing.model.dto.TestIn;
import com.example.javaserver.testing.model.dto.ThemeIn;
import com.example.javaserver.testing.service.QuestionService;
import com.example.javaserver.testing.service.ResultService;
import com.example.javaserver.testing.service.TestService;
import com.example.javaserver.testing.service.ThemeService;
import com.example.javaserver.user.model.UserRole;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

@RestController
@RequestMapping(value = "api/testing/")
public class TestingController {

    private final QuestionService questionService;

    private final ResultService resultService;

    private final TestService testService;

    private final ThemeService themeService;

    private final RequestHandlerService requestHandlerService;

    @Autowired
    public TestingController(QuestionService questionService, ResultService resultService, TestService testService, ThemeService themeService, RequestHandlerService requestHandlerService) {
        this.questionService = questionService;
        this.resultService = resultService;
        this.testService = testService;
        this.themeService = themeService;
        this.requestHandlerService = requestHandlerService;
    }



    @GetMapping("/hello")
    public ResponseEntity<?> hi() {
        return new ResponseEntity<>(new Message("Привет я работаю"), HttpStatus.OK);
    }

    @ApiOperation(value = "")
    @PostMapping("/questions")
    public ResponseEntity<?> createQuestions(@RequestBody TestIn testIn, @RequestHeader(name = "token") String token) {
        return requestHandlerService.proceed(token, userContext -> questionService.createQuestions(testIn),
                EnumSet.of(UserRole.ADMIN, UserRole.TEACHER));
    }

    @PutMapping("/questions")
    public ResponseEntity<?> updateQuestions(@RequestBody TestIn testIn, @RequestHeader(name = "token") String token) {
        return requestHandlerService.proceed(token, userContext -> questionService.updateQuestions(testIn),
                EnumSet.of(UserRole.ADMIN, UserRole.TEACHER));
    }

    @DeleteMapping("/questions")
    public ResponseEntity<?> deleteManyQuestions(@RequestBody List<Long> ids,
                                                 @RequestHeader(name = "token") String token) {
        return requestHandlerService.proceed(token, userContext -> questionService.deleteManyQuestions(ids),
                EnumSet.of(UserRole.ADMIN, UserRole.TEACHER));
    }


    @GetMapping("/questions")
    public ResponseEntity<?> fetchThemesBySubjectId(@RequestParam(value = "subj_id") Long subjectId,
                                                    @RequestHeader(name = "token") String token) {
        return requestHandlerService.proceed(token, userContext -> themeService.fetchSubjectThemes(subjectId),
                EnumSet.allOf(UserRole.class));
    }
    @PostMapping("/theme")
    public ResponseEntity<?> createTheme(@RequestBody ThemeIn themeIn, @RequestHeader(name = "token") String token){
        return requestHandlerService.proceed(token, userContext -> themeService.createTheme(themeIn),
                EnumSet.of(UserRole.ADMIN, UserRole.TEACHER));
    }
    @DeleteMapping("/question")
    public ResponseEntity<?> deleteOneQuestion(@RequestParam(value = "limit") Long id,
                                               @RequestHeader(name = "token") String token) {
        return requestHandlerService.proceed(token, userContext ->
                        questionService.deleteManyQuestions(new ArrayList<>(Collections.singletonList(id))),
                EnumSet.of(UserRole.ADMIN, UserRole.TEACHER));
    }

    @DeleteMapping("/all-questions")
    public ResponseEntity<?> deleteAllQuestion(@RequestHeader(name = "token") String token) {
        return requestHandlerService.proceed(token, userContext -> questionService.deleteAllQuestions(),
                EnumSet.of(UserRole.ADMIN, UserRole.TEACHER));
    }

    @GetMapping("/test")
    public ResponseEntity<?> makeTest(
            @RequestParam(value = "subj_id") Long subjectId,
            @RequestParam(value = "theme_id") Long themeId,
            @RequestParam(value = "limit", required = false) Integer countOfQuestions,
            @RequestHeader(name = "token") String token) {
        return requestHandlerService.proceed(token, userContext ->
                        testService.createTest(themeId, countOfQuestions),
                EnumSet.allOf(UserRole.class));
    }

    @PostMapping("/test/checking")
    public ResponseEntity<?> checkTest(@RequestBody List<AnswerInOut> userTest,
                                       @RequestHeader(name = "token") String token) {
        return requestHandlerService.proceed(token, userContext ->
                        testService.checkTest(userTest, userContext),
                EnumSet.allOf(UserRole.class));
    }

    @GetMapping("/test/result/all")
    public ResponseEntity<?> fetchAllPassedTests(@RequestHeader(name = "token") String token) {
        return requestHandlerService.proceed(token, resultService::formUserPassedTest,
                EnumSet.allOf(UserRole.class));
    }

    @GetMapping("/test/result")
    public ResponseEntity<?> fetchPassedTests(@RequestParam(value = "theme_id") Long themeId,
                                              @RequestParam(value = "user_id") Integer userId,
                                              @RequestHeader(name = "token") String token) {
        return requestHandlerService.proceed(token,
                userContext -> resultService.fetchUserPassedTestsByThemeAndUserId(userId, themeId),
                EnumSet.allOf(UserRole.class));
    }

    @GetMapping("/test/passed-themes")
    public ResponseEntity<?> fetchPassedThemes(@RequestParam(value = "subj_id") Long subjectId,
                                               @RequestParam(value = "user_id") Integer userId,
                                               @RequestHeader(name = "token") String token) {
        return requestHandlerService.proceed(token,
                userContext -> resultService.fetchUserPassedThemesBySubjectIdAndUserId(userId, subjectId),
                EnumSet.allOf(UserRole.class));
    }

    @GetMapping("/all-questions")
    public ResponseEntity<?> findAll(@RequestHeader(name = "token") String token) {
        return requestHandlerService.proceed(token, userContext -> questionService.fetchAllQuestions(),
                EnumSet.of(UserRole.ADMIN, UserRole.TEACHER));
    }
}
