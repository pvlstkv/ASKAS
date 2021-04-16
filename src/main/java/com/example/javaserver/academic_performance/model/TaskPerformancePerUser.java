package com.example.javaserver.academic_performance.model;

import com.example.javaserver.study.model.Work;
import com.example.javaserver.user.model.User;

import java.util.ArrayList;
import java.util.List;

public class TaskPerformancePerUser {
    private String firstName;
    private String lastName;
    private String patronymic;
    private Integer userId;
    private List<Work> works = new ArrayList<>();

    public TaskPerformancePerUser() {

    }

    public TaskPerformancePerUser(String firstName, String lastName, String patronymic, Integer userId, List<Work> works) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.userId = userId;
        this.works = works;
    }

    public TaskPerformancePerUser(User user, List<Work> works) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.patronymic = user.getPatronymic();
        this.userId = user.getId();
        this.works = works;
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

    public List<Work> getWorks() {
        return works;
    }

    public void setWorks(List<Work> works) {
        this.works = works;
    }

    public void addWorks(List<Work> works) {
        if (works != null) {
            this.works.addAll(works);
        }
    }
}
