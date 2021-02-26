package com.example.javaserver.schedule.controller.model;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private String nameGroup;
    private List<Day> Days = new ArrayList<>();

    public Group(String nameGroup, List<Day> days) {
        this.nameGroup = nameGroup;
        Days = days;
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
