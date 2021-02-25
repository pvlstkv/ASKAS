package com.example.javaserver.model.common_data;

import com.example.javaserver.model.User;


import javax.persistence.*;
import java.io.Serializable;
import java.time.OffsetDateTime;

@SuppressWarnings("unused")
@Entity

@Table(name = "studyings")
public class Studying implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    private User user;

    @OneToOne
    private Subject subject;

    private int numberOfSemesters;

    private Mark mark;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    public Studying() { }

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
