package com.example.javaserver.testing.controller;

import com.example.javaserver.testing.model.Question;
import com.example.javaserver.testing.model.dto.TestIn;
import com.example.javaserver.testing.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/testing/")
public class QuestionController {
    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping("/questions")
    @ResponseStatus(HttpStatus.CREATED)
    @Secured({"TEACHER", "ADMIN"})
    public void createQuestions(
            @RequestBody TestIn testIn
    ) {
        questionService.createQuestions(testIn);
    }

    @PutMapping("/questions")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"TEACHER", "ADMIN"})
    public ResponseEntity<?> updateQuestions(
            @RequestBody TestIn testIn
    ) {
        return questionService.updateQuestions(testIn);
    }

    @DeleteMapping("/questions")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"TEACHER", "ADMIN"})
    public void deleteManyQuestions(
            @RequestBody List<Long> ids
    ) {
        questionService.deleteManyQuestions(ids);
    }

    @DeleteMapping("/all-questions")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"TEACHER", "ADMIN"})
    public void deleteAllQuestion() {
        questionService.deleteAllQuestions();
    }

    @GetMapping("/all-questions")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"TEACHER", "ADMIN"})
    public List<Question> fetchAllQuestionInTheme(
            @RequestParam(value = "theme_id") Long themeId
    ) {
        return questionService.fetchAllQuestions(themeId);
    }

}
