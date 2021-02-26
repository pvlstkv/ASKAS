package com.example.javaserver.common_data.model;

import com.example.javaserver.user.model.User;


import javax.persistence.*;
import java.io.Serializable;
import java.time.OffsetDateTime;

@SuppressWarnings("unused")
@Entity

@Table(name = "marks")
public class Mark implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    private User user;

    @OneToOne
    private SubjectSemester subjectSemester;

    // wtf?
    //private int numberOfSemesters;

    private Grade grade;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    public Mark() { }

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
