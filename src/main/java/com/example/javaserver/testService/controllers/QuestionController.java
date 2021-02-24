package com.example.javaserver.testService.controllers;


import com.example.javaserver.testService.models.InOutComingModels.RequestedAnswer;
//import com.example.javaserver.testService.models.InOutComingModels.RequestedTest;
//import com.example.javaserver.testService.models.Question;
//import com.example.javaserver.testService.repo.QuestionRepo;
import com.example.javaserver.testService.services.QuestionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
//@CrossOrigin(origins = "http://localhost:8080")
public class QuestionController {

//    @Autowired
//    private QuestionRepo questionRepo;

    @Autowired
    private QuestionService questionService;


    @ApiOperation(value = "")
    @PostMapping("/questions")
//    public ResponseEntity<?> createQuestions(@RequestBody RequestedTest requestedTest, @RequestHeader (name="Authorization") String token) {
    public ResponseEntity<?> createQuestions( @RequestHeader (name="Authorization") String token) {
        return questionService.createQuestions(token);
    }


    @PutMapping("/questions")
//    public ResponseEntity<?> updateQuestions(@RequestBody RequestedTest requestedTest,  @RequestHeader (name="Authorization") String token) {
    public ResponseEntity<?> updateQuestions(  @RequestHeader (name="Authorization") String token) {
        return questionService.updateQuestions(token);
    }

    @DeleteMapping("/questions")
    public ResponseEntity<?> deleteManyQuestions(@RequestBody List<Long> ids,  @RequestHeader (name="Authorization") String token){
        return questionService.deleteManyQuestions(ids, token);
    }

    @DeleteMapping("/question")
    public ResponseEntity<?> deleteOneQuestion(@RequestParam (value = "limit") Long id,  @RequestHeader (name="Authorization") String token){
        return questionService.deleteManyQuestions(new ArrayList<>(Collections.singletonList(id)), token);
    }

    @DeleteMapping("/all-questions")
    public ResponseEntity<?> deleteAllQuestion(@RequestHeader (name="Authorization") String token){
        return questionService.deleteAllQuestions(token);
    }

    @GetMapping("/test/{subject}")
    public ResponseEntity<?> getTest(@PathVariable String subject, @RequestParam (value = "limit") Integer count,
                                     @RequestHeader (name="Authorization") String token){
        return questionService.createTest(subject, count, token);
    }

    @PostMapping("/test/checking")
    public ResponseEntity<?> checkTest(@RequestBody List<RequestedAnswer> requestedAnswers,
                                       @RequestHeader (name="Authorization") String token){
        return questionService.checkTest(requestedAnswers, token);
    }

    @GetMapping("/all-questions")
    public ResponseEntity<?> findAll( @RequestHeader (name="Authorization") String token) {
//        List<Question> question = questionRepo.findAll();
//        return new ResponseEntity<>(question, HttpStatus.FOUND);
        return new ResponseEntity<>(HttpStatus.FOUND);
    }

    @GetMapping("/all-questions/{subject}")
    public ResponseEntity<?> findAllBySubject(@PathVariable (value = "subject") String subject,  @RequestHeader (name="Authorization") String token) {
        return questionService.findAllBySubject(subject, token);
    }

//    @GetMapping("{id}")
//    public ResponseEntity<?> findById(@PathVariable (value = "id") Long subject) {
//        Question q = questionRepo.findById(subject).get();
//        Question question = q;
//        return new ResponseEntity<>(question,HttpStatus.OK);
//    }


}
