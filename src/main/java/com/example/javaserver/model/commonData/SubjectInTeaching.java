//package com.example.javaserver.model.commonData;
//
//import com.example.javaserver.configuration.Marks;
//import lombok.Data;
//
//import javax.persistence.*;
//import java.time.OffsetDateTime;
//
//@Entity
//public class SubjectInTeaching {
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE)
//    private long id;
//
//    @OneToOne
//    private Subject subject;
//    private Marks mark;
//
//    private OffsetDateTime createdAt;
//    private OffsetDateTime updatedAt;
//
//    public SubjectInTeaching(long id, Subject subject, Marks mark, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
//        this.id = id;
//        this.subject = subject;
//        this.mark = mark;
//        this.createdAt = createdAt;
//        this.updatedAt = updatedAt;
//    }
//
//    public SubjectInTeaching() {
//
//    }
//
//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }
//
//    public Subject getSubject() {
//        return subject;
//    }
//
//    public void setSubject(Subject subject) {
//        this.subject = subject;
//    }
//
//    public Marks getMark() {
//        return mark;
//    }
//
//    public void setMark(Marks mark) {
//        this.mark = mark;
//    }
//
//    public OffsetDateTime getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(OffsetDateTime createdAt) {
//        this.createdAt = createdAt;
//    }
//
//    public OffsetDateTime getUpdatedAt() {
//        return updatedAt;
//    }
//
//    public void setUpdatedAt(OffsetDateTime updatedAt) {
//        this.updatedAt = updatedAt;
//    }
//}
