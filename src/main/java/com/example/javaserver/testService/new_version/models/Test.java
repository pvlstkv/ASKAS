package com.example.javaserver.testService.new_version.models;//package com.example.testService.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String subject_name;
    @ElementCollection
    private List<Long> list_of_question_id;

    public Test() {
    }

    public Test(String subject_name, List<Long> list_of_question_id) {
        this.subject_name = subject_name;
        this.list_of_question_id = list_of_question_id;
    }

    public String getSubject() {
        return subject_name;
    }

    public void setSubject(String subject_name) {
        this.subject_name = subject_name;
    }

    public List<Long> getList_of_question_id() {
        return list_of_question_id;
    }

    public void setList_of_question_id(List<Long> list_of_question_id) {
        this.list_of_question_id = list_of_question_id;
    }
}
