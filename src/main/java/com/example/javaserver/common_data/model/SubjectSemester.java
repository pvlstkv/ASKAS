package com.example.javaserver.common_data.model;


import javax.persistence.*;
import java.time.OffsetDateTime;

@SuppressWarnings("unused")
@Entity

@Table(name = "subject_semesters")
public class SubjectSemester {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    int numberOfSemester;

    private SubjectControlType controlType;

    boolean hasCourseWork;

    boolean hasCourseProject;

    @ManyToOne(fetch = FetchType.LAZY)
    private Subject subject;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    // in the nearest future something like this
    // int countOfLecture;
    // int countOfLabWork;

    public SubjectSemester() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getNumberOfSemester() {
        return numberOfSemester;
    }

    public void setNumberOfSemester(int numberOfSemester) {
        this.numberOfSemester = numberOfSemester;
    }

    public SubjectControlType getControlType() {
        return controlType;
    }

    public void setControlType(SubjectControlType controlType) {
        this.controlType = controlType;
    }

    public boolean isHasCourseWork() {
        return hasCourseWork;
    }

    public void setHasCourseWork(boolean hasCourseWork) {
        this.hasCourseWork = hasCourseWork;
    }

    public boolean isHasCourseProject() {
        return hasCourseProject;
    }

    public void setHasCourseProject(boolean hasCourseProject) {
        this.hasCourseProject = hasCourseProject;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
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
