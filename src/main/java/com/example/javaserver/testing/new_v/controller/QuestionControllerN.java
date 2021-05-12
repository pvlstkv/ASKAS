package com.example.javaserver.testing.new_v.controller;

import com.example.javaserver.testing.new_v.service.QuestionServiceN;
import com.example.javaserver.testing.new_v.dto.test.TestDto;
import com.example.javaserver.testing.new_v.model.question.QuestionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(value = "testing/new/")
public class QuestionControllerN {
    public QuestionServiceN questionServiceN;

    @Autowired
    public QuestionControllerN(QuestionServiceN questionServiceN) {
        this.questionServiceN = questionServiceN;
    }


    @PostMapping("/questions")
    @ResponseStatus(HttpStatus.CREATED)
    @Secured({"TEACHER", "ADMIN"})
    public void create(@RequestBody TestDto testDto) {
        System.out.println(testDto);
        questionServiceN.saveQuestions(testDto);
        int s = 0;
    }

    @PutMapping("/questions")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"TEACHER", "ADMIN"})
    public void update(@RequestBody TestDto testDto) {
        questionServiceN.saveQuestions(testDto);
    }

    @DeleteMapping("/questions")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"TEACHER", "ADMIN"})
    public void delete(@RequestBody Set<Long> ids) {
        questionServiceN.deleteQuestions(ids);
    }


    @GetMapping("/question")
    public QuestionData get(@RequestParam Long id) {
        return questionServiceN.get(id);
    }


}
