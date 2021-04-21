package com.example.javaserver.academic_performance.model;

import com.example.javaserver.study.model.Work;
import com.example.javaserver.user.model.User;

public class TaskPerformancePerUser {
    private String firstName;
    private String lastName;
    private String patronymic;
    private Integer userId;
    private Work work;

    public TaskPerformancePerUser() {
    }

    public TaskPerformancePerUser(String firstName, String lastName, String patronymic, Integer userId, Work work) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.userId = userId;
        this.work = work;
    }

    public TaskPerformancePerUser(User user, Work work) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.patronymic = user.getPatronymic();
        this.userId = user.getId();
        this.work = work;
    }

    public TaskPerformancePerUser(User user) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.patronymic = user.getPatronymic();
        this.userId = user.getId();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Work getWork() {
        return work;
    }

    public void setWork(Work work) {
        this.work = work;
    }
}
