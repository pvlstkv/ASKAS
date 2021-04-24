package com.example.javaserver.testing.n.controller;

import com.example.javaserver.testing.n.dto.Exp;
import com.example.javaserver.testing.n.dto.question.QuestionDataDto;
import com.example.javaserver.testing.n.dto.test.TestDto;
import com.example.javaserver.testing.n.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
public class QController {
    public QuestionService questionService;

    @Autowired
    public QController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping("/question")
    public void create(@RequestBody TestDto testDto) {
        System.out.println(testDto);
        questionService.createQuestions(testDto);
        int s = 0;
    }

    @PostMapping("/exp")
    public void create(@RequestBody Exp e) {
        Collection<Object> map = (Collection) e.getObj();
        System.out.println(e);
    }

}
