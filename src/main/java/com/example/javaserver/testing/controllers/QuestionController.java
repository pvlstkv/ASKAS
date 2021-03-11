package com.example.javaserver.testing.controllers;


import com.example.javaserver.general.model.Message;
import com.example.javaserver.testing.models.dto.AnswerInOut;
import com.example.javaserver.testing.models.dto.TestIn;
import com.example.javaserver.testing.models.Question;
import com.example.javaserver.testing.repo.QuestionRepo;
import com.example.javaserver.testing.services.QuestionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value = "api/testing/")
public class QuestionController {

    @Autowired
    private QuestionRepo questionRepo;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/hello")
    public ResponseEntity<?> hi() {
        return new ResponseEntity<>(new Message("Привет я работаю"), HttpStatus.OK);
    }

    @ApiOperation(value = "")
    @PostMapping("/questions")
    public ResponseEntity<?> createQuestions(@RequestBody TestIn testIn, @RequestHeader(name = "token") String token) {
        return questionService.createQuestions(testIn, token);
    }


    @PutMapping("/questions")
    public ResponseEntity<?> updateQuestions(@RequestBody TestIn testIn, @RequestHeader(name = "token") String token) {
        return questionService.updateQuestions(testIn, token);
    }

    @DeleteMapping("/questions")
    public ResponseEntity<?> deleteManyQuestions(@RequestBody List<Long> ids, @RequestHeader(name = "token") String token) {
        return questionService.deleteManyQuestions(ids, token);
    }


    @GetMapping("/questions")
    public ResponseEntity<?> fetchThemesBySubjectId(@RequestParam(value = "subj_id") Long id, @RequestHeader(name = "token") String token) {
        return questionService.fetchSubjectThemes(id, token);
    }

    @DeleteMapping("/question")
    public ResponseEntity<?> deleteOneQuestion(@RequestParam(value = "limit") Long id, @RequestHeader(name = "token") String token) {
        return questionService.deleteManyQuestions(new ArrayList<>(Collections.singletonList(id)), token);
    }

    @DeleteMapping("/all-questions")
    public ResponseEntity<?> deleteAllQuestion(@RequestHeader(name = "token") String token) {
        return questionService.deleteAllQuestions(token);
    }

    @GetMapping("/test")
    public ResponseEntity<?> getTest(/*PathVariable String subject,*/
            @RequestParam(value = "subj_id") Long subjectId,
            @RequestParam(value = "theme_id") Long themeId,
            @RequestParam(value = "limit") Integer count,
            @RequestHeader(name = "token") String token) {
        return questionService.createTest(subjectId, themeId, count, token);
    }

    @PostMapping("/test/checking")
    public ResponseEntity<?> checkTest(@RequestBody List<AnswerInOut> requestedAnswers,
                                       @RequestHeader(name = "token") String token) {
        return questionService.checkTest(requestedAnswers, token);
    }

    @GetMapping("/test/result")
    public ResponseEntity<?> fetchPassedTests(@RequestHeader(name = "token") String token) {
        return questionService.fetchUserPassedTest(token);
    }


    @GetMapping("/all-questions")
    public ResponseEntity<?> findAll(@RequestHeader(name = "token") String token) {
        List<Question> question = questionRepo.findAll();
        return new ResponseEntity<>(question, HttpStatus.FOUND);
    }
//
//    @GetMapping("/all-questions/{subject}")
//    public ResponseEntity<?> findAllBySubject(@PathVariable(value = "subject") String subject, @RequestHeader(name = "Authorization") String token) {
//        return questionService.findAllBySubject(subject, token);
//    }

}
