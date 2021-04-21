package com.example.javaserver.testing.model.new_;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QController {
    @PostMapping("/question")
    public void create(@RequestBody String json){
        System.out.println(json);
    }
}
