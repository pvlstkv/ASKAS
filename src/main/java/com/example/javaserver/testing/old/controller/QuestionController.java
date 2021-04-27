package com.example.javaserver.testing.old.controller;

import com.example.javaserver.testing.old.model.Question;
import com.example.javaserver.testing.old.model.dto.TestIn;
import com.example.javaserver.testing.old.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public void updateQuestions(
            @RequestBody TestIn testIn
    ) {
        questionService.updateQuestions(testIn);
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
//
//    @GetMapping("/exp")
//    @ResponseStatus(HttpStatus.OK)
//    @Secured({"TEACHER", "ADMIN"})
//    public QuestionMatch exp(){
//        List<Match> matches = new ArrayList<>();
//        Match match = new Match(1L, new AnswerChoice("слово из левой колонки"),  new AnswerChoice("слово из правой колонки"));
//        matches.add(match);
//        matches.add(new Match(2L,  new AnswerChoice("key"), new AnswerChoice("value")));
//        QuestionMatch questionMatch = new QuestionMatch(1L, "What is it?", QuestionType.CHOOSE, matches,3.0);
//        return questionMatch;
//    }
}
