package com.example.javaserver.model.commonData;

import com.example.javaserver.configuration.Marks;
import com.example.javaserver.model.Consumer;

import javax.persistence.*;
import java.time.OffsetDateTime;


@Entity
public class Semester {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @OneToOne
    private Consumer consumer;
    @OneToOne
    private Subject subject;

    private Marks marks;
    private OffsetDateTime startDate;
    private OffsetDateTime endDate;

    private int numberOfSemester;

    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private boolean isGoing;

    public Semester(long id, Consumer consumer, Subject subject, Marks marks, OffsetDateTime startDate, OffsetDateTime endDate, int numberOfSemester, OffsetDateTime createdAt, OffsetDateTime updatedAt, boolean isGoing) {
        this.id = id;
        this.consumer = consumer;
        this.subject = subject;
        this.marks = marks;
        this.startDate = startDate;
        this.endDate = endDate;
        this.numberOfSemester = numberOfSemester;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isGoing = isGoing;
    }

    public Semester() {

    }

    public Semester(Consumer consumer, Subject subject, int numberOfSemester, boolean isGoing) {
        this.consumer = consumer;
        this.subject = subject;
        this.numberOfSemester = numberOfSemester;
        this.isGoing = isGoing;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Consumer getConsumer() {
        return consumer;
    }

    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public int getNumberOfSemester() {
        return numberOfSemester;
    }

    public void setNumberOfSemester(int numberOfSemester) {
        this.numberOfSemester = numberOfSemester;
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

    public Marks getMarks() {
        return marks;
    }

    public void setMarks(Marks marks) {
        this.marks = marks;
    }

    public OffsetDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(OffsetDateTime startDate) {
        this.startDate = startDate;
    }

    public OffsetDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(OffsetDateTime endDate) {
        this.endDate = endDate;
    }

    public boolean isGoing() {
        return isGoing;
    }

    public void setGoing(boolean going) {
        isGoing = going;
    }
}
