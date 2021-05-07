package com.example.javaserver.schedule.controller.dto;

import java.util.ArrayList;
import java.util.List;

public class DayTeacher {
    private Integer number_day;
    private Integer numberWeek;

    private List<CoupleTeacher> coupels = new ArrayList<>();

    public DayTeacher() {
    }

    public DayTeacher(Integer number_day, Integer numberWeek) {
        this.number_day = number_day;
        this.numberWeek = numberWeek;
    }

    public Integer getNumber_day() {
        return number_day;
    }

    public void setNumber_day(Integer number_day) {
        this.number_day = number_day;
    }

    public Integer getNumberWeek() {
        return numberWeek;
    }

    public void setNumberWeek(Integer numberWeek) {
        this.numberWeek = numberWeek;
    }

    public List<CoupleTeacher> getCoupels() {
        return coupels;
    }

    public void setCoupels(List<CoupleTeacher> coupels) {
        this.coupels = coupels;
    }
}
