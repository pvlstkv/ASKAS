package com.example.javaserver.schedule.controller.dto;

import java.util.ArrayList;
import java.util.List;

public class GroupTeacher {
    private String nameTeacher;
    private List<DayTeacher> Days = new ArrayList<>();

    public GroupTeacher() {
    }

    public GroupTeacher(String nameTeacher) {
        this.nameTeacher = nameTeacher;
    }

    public String getNameTeacher() {
        return nameTeacher;
    }

    public void setNameTeacher(String nameTeacher) {
        this.nameTeacher = nameTeacher;
    }

    public List<DayTeacher> getDays() {
        return Days;
    }

    public void setDays(List<DayTeacher> days) {
        Days = days;
    }
}
