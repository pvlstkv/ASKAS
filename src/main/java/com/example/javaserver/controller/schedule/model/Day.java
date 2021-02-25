package com.example.javaserver.controller.schedule.model;

import java.util.ArrayList;
import java.util.List;

public class Day {
    private Integer number_day;
    private Integer numberWeek;

    private List<Couple> coupels = new ArrayList<>();

    public Day(List<Couple> coupels) {
        this.coupels = coupels;
    }

    public Day() {
    }

    public Day(Integer number_day, Integer numberWeek) {
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

    public List<Couple> getCoupels() {
        return coupels;
    }

    public void setCoupels(List<Couple> coupels) {
        this.coupels = coupels;
    }
}
