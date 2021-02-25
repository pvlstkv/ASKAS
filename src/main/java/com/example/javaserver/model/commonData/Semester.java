package com.example.javaserver.model.commonData;

import com.example.javaserver.model.User;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.OffsetDateTime;

@SuppressWarnings("unused")
@Entity
@Data
@Table(name = "semesters")
public class Semester implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @OneToOne
    private User user;

    @OneToOne
    private SubjectInTeaching subjectInTeaching;

    private int numberOfSemesters;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    public Semester() { }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public SubjectInTeaching getSubjectInTeaching() {
        return subjectInTeaching;
    }

    public void setSubjectInTeaching(SubjectInTeaching subjectInTeaching) {
        this.subjectInTeaching = subjectInTeaching;
    }

    public int getNumberOfSemesters() {
        return numberOfSemesters;
    }

    public void setNumberOfSemesters(int numberOfSemesters) {
        this.numberOfSemesters = numberOfSemesters;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
