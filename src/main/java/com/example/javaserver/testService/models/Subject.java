//package com.example.javaserver.testService.models;
//
//import com.fasterxml.jackson.annotation.JsonManagedReference;
//
//import javax.persistence.*;
//import java.util.List;
//
//@Entity
//@Table(name = "subjects")
//public class Subject {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
//    private String name;
//    @OneToMany(mappedBy = "subject")
//    private List<Question> question;
//
//    public Subject() {
//    }
//
//    public Subject(String name) {
//        this.name = name;
//    }
//
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//
//}
