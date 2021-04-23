package com.example.javaserver.testing.n.controller;

import com.example.javaserver.testing.n.dto.Exp;
import com.example.javaserver.testing.n.dto.question.QuestionDataDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Map;

@RestController
public class QController {
    @PostMapping("/question")
    public void create(@RequestBody QuestionDataDto json) {
        System.out.println(json);

        Map<String, Object> a = ((Map<String, Object>) json.getAnswers().get(0).get("key"));

        int s = 0;
    }

    @PostMapping("/exp")
    public void create(@RequestBody Exp e) {
        Collection<Object> map = (Collection) e.getObj();
        System.out.println(e);
    }
}
