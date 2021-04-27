//package com.example.javaserver.testing.model;
//
//import javax.persistence.*;
//
//@Entity
//public class Match {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @OneToOne
//    private AnswerChoice key;
//    @OneToOne
//    private AnswerChoice value;
//
//    public Match() {
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public AnswerChoice getKey() {
//        return key;
//    }
//
//    public void setKey(AnswerChoice key) {
//        this.key = key;
//    }
//
//    public AnswerChoice getValue() {
//        return value;
//    }
//
//    public void setValue(AnswerChoice value) {
//        this.value = value;
//    }
//
//    public Match(Long id, AnswerChoice key, AnswerChoice value) {
//        this.id = id;
//        this.key = key;
//        this.value = value;
//    }
//}
