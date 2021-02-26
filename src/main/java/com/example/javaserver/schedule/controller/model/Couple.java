package com.example.javaserver.schedule.controller.model;

public class Couple {
    private Integer pair_number;
    private String teacher;
    private String subject;
    private Integer subgroup;
    private String place;
    private Integer typeSubject;
    private String info;

    public Couple() {
    }

    public Integer getPair_number() {
        return pair_number;
    }

    public void setPair_number(Integer pair_number) {
        this.pair_number = pair_number;
    }


    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Integer getSubgroup() {
        return subgroup;
    }

    public void setSubgroup(Integer subgroup) {
        this.subgroup = subgroup;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Integer getTypeSubject() {
        return typeSubject;
    }

    public void setTypeSubject(Integer typeSubject) {
        this.typeSubject = typeSubject;
    }


    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
