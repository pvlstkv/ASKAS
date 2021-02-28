package com.example.javaserver.testService.old_version.controllers;


import com.example.javaserver.general.model.Message;
import com.example.javaserver.testService.old_version.models.InOutComingModels.RequestedAnswer;
import com.example.javaserver.testService.old_version.models.InOutComingModels.RequestedTest;
import com.example.javaserver.testService.old_version.models.Question;
import com.example.javaserver.testService.old_version.repo.QuestionRepo;
import com.example.javaserver.testService.old_version.services.QuestionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value = "api/testing")
public class QuestionController {

    @Autowired
    private QuestionRepo questionRepo;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/hello")
    public ResponseEntity<?> hi(){
        return new ResponseEntity<>(new Message("Привет я работаю"),HttpStatus.OK);
    }

    @ApiOperation(value = "")
    @PostMapping("/questions")
    public ResponseEntity<?> createQuestions(@RequestBody RequestedTest requestedTest, @RequestHeader(name = "Authorization") String token) {
        return questionService.createQuestions(requestedTest, token);
    }


    @PutMapping("/questions")
    public ResponseEntity<?> updateQuestions(@RequestBody RequestedTest requestedTest, @RequestHeader(name = "Authorization") String token) {
        return questionService.updateQuestions(requestedTest, token);
    }

    @DeleteMapping("/questions")
    public ResponseEntity<?> deleteManyQuestions(@RequestBody List<Long> ids, @RequestHeader(name = "Authorization") String token) {
        return questionService.deleteManyQuestions(ids, token);
    }

    @DeleteMapping("/question")
    public ResponseEntity<?> deleteOneQuestion(@RequestParam(value = "limit") Long id, @RequestHeader(name = "Authorization") String token) {
        return questionService.deleteManyQuestions(new ArrayList<>(Collections.singletonList(id)), token);
    }

    @DeleteMapping("/all-questions")
    public ResponseEntity<?> deleteAllQuestion(@RequestHeader(name = "Authorization") String token) {
        return questionService.deleteAllQuestions(token);
    }

    @GetMapping("/test/{subject}")
    public ResponseEntity<?> getTest(@PathVariable String subject, @RequestParam(value = "limit") Integer count,
                                     @RequestHeader(name = "Authorization") String token) {
        return questionService.createTest(subject, count, token);
    }

    @PostMapping("/test/checking")
    public ResponseEntity<?> checkTest(@RequestBody List<RequestedAnswer> requestedAnswers,
                                       @RequestHeader(name = "Authorization") String token) {
        return questionService.checkTest(requestedAnswers, token);
    }

    @GetMapping("/all-questions")
    public ResponseEntity<?> findAll(@RequestHeader(name = "Authorization") String token) {
        List<Question> question = questionRepo.findAll();
        return new ResponseEntity<>(question, HttpStatus.FOUND);
    }

    @GetMapping("/all-questions/{subject}")
    public ResponseEntity<?> findAllBySubject(@PathVariable(value = "subject") String subject, @RequestHeader(name = "Authorization") String token) {
        return questionService.findAllBySubject(subject, token);
    }

}
