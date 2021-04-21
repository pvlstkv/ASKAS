package com.example.javaserver.schedule.controller.dto;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private String nameGroup;
    private List<Day> Days = new ArrayList<>();

    public Group() {
    }




    public Group(String nameGroup) {
        this.nameGroup = nameGroup;
    }

    public String getNameGroup() {
        return nameGroup;
    }

    public void setNameGroup(String nameGroup) {
        this.nameGroup = nameGroup;
    }

    public List<Day> getDays() {
        return Days;
    }

    public void setDays(List<Day> days) {
        Days = days;
    }
}
