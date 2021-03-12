package com.example.javaserver.testing.controllers;


import com.example.javaserver.general.model.Message;
import com.example.javaserver.general.service.RequestHandlerService;
import com.example.javaserver.testing.models.dto.AnswerInOut;
import com.example.javaserver.testing.models.dto.TestIn;
import com.example.javaserver.testing.services.QuestionService;
import com.example.javaserver.testing.services.ResultService;
import com.example.javaserver.testing.services.TestService;
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

    private final RequestHandlerService requestHandlerService;

    public TestingController(QuestionService questionService, ResultService resultService, TestService testService, RequestHandlerService requestHandlerService) {
        this.questionService = questionService;
        this.resultService = resultService;
        this.testService = testService;
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
    public ResponseEntity<?> fetchThemesBySubjectId(@RequestParam(value = "subj_id") Long id,
                                                    @RequestHeader(name = "token") String token) {
        return requestHandlerService.proceed(token, userContext -> questionService.fetchSubjectThemes(id),
                EnumSet.of(UserRole.ADMIN, UserRole.TEACHER, UserRole.USER));
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
    public ResponseEntity<?> getTest(
            @RequestParam(value = "subj_id") Long subjectId,
            @RequestParam(value = "theme_id") Long themeId,
            @RequestParam(value = "limit") Integer countOfQuestions,
            @RequestHeader(name = "token") String token) {
        return requestHandlerService.proceed(token, userContext ->
                        testService.createTest(themeId, countOfQuestions),
                EnumSet.of(UserRole.ADMIN, UserRole.TEACHER, UserRole.USER));
    }

    @PostMapping("/test/checking")
    public ResponseEntity<?> checkTest(@RequestBody List<AnswerInOut> userTest,
                                       @RequestHeader(name = "token") String token) {
        return requestHandlerService.proceed(token, userContext ->
                        testService.checkTest(userTest, userContext),
                EnumSet.of(UserRole.ADMIN, UserRole.TEACHER, UserRole.USER));
    }

    @GetMapping("/test/result")
    public ResponseEntity<?> fetchPassedTests(@RequestHeader(name = "token") String token) {
        return requestHandlerService.proceed(token, resultService::formUserPassedTest,
                EnumSet.of(UserRole.ADMIN, UserRole.TEACHER, UserRole.USER));

    }

    @GetMapping("/all-questions")
    public ResponseEntity<?> findAll(@RequestHeader(name = "token") String token) {
        return requestHandlerService.proceed(token, userContext -> questionService.fetchAllQuestions(),
                EnumSet.of(UserRole.ADMIN, UserRole.TEACHER));
    }
}
