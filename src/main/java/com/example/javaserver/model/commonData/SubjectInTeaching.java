package com.example.javaserver.model.commonData;

import lombok.Data;

import javax.persistence.*;
import java.time.OffsetDateTime;

@SuppressWarnings("unused")
@Entity
@Data
@Table(name = "subjects_in_teaching")
public class SubjectInTeaching {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @OneToOne
    private Subject subject;

    private Mark mark;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    public SubjectInTeaching() { }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Mark getMark() {
        return mark;
    }

    public void setMark(Mark mark) {
        this.mark = mark;
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
