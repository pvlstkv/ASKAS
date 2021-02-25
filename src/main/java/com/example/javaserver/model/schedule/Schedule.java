package com.example.javaserver.model.schedule;

import javax.persistence.*;

@Entity
@Table(name = "schedule")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nameGroup;
    private Integer numberDay;
    private Integer numberWeek;
    private Integer pairNumber;
    private String teacher;
    private String subject;
    private Integer subgroup;
    private String place;
    private Integer typeSubject;
    private String info;

    public Schedule() {
    }

    public Schedule(Long id, String nameGroup, Integer numberDay, Integer numberWeek,
                    Integer pairNumber, String teacher, String subject,
                    Integer subgroup, String place,
                    Integer typeSubject, String info) {
        this.id = id;
        this.nameGroup = nameGroup;
        this.numberDay = numberDay;
        this.numberWeek = numberWeek;
        this.pairNumber = pairNumber;
        this.teacher = teacher;
        this.subject = subject;
        this.subgroup = subgroup;
        this.place = place;
        this.typeSubject = typeSubject;
        this.info = info;
    }

    public Schedule(Integer numberDay, Integer numberWeek, Integer pairNumber, String info) {
        this.numberDay = numberDay;
        this.numberWeek = numberWeek;
        this.pairNumber = pairNumber;
        this.info = info;
    }

    public Schedule(Schedule schedule) {
        this.nameGroup = schedule.getNameGroup();
        this.numberDay = schedule.getNumberDay();
        this.numberWeek = schedule.getNumberWeek();
        this.pairNumber = schedule.getPairNumber();
        this.teacher = schedule.getTeacher();
        this.subject = schedule.getSubject();
        this.subgroup = schedule.getSubgroup();
        this.place = schedule.getPlace();
        this.typeSubject = schedule.getTypeSubject();
        this.info = schedule.getInfo();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameGroup() {
        return nameGroup;
    }

    public void setNameGroup(String nameGroup) {
        this.nameGroup = nameGroup;
    }

    public Integer getNumberDay() {
        return numberDay;
    }

    public void setNumberDay(Integer numberDay) {
        this.numberDay = numberDay;
    }

    public Integer getNumberWeek() {
        return numberWeek;
    }

    public void setNumberWeek(Integer numberWeek) {
        this.numberWeek = numberWeek;
    }

    public Integer getPairNumber() {
        return pairNumber;
    }

    public void setPairNumber(Integer pairNumber) {
        this.pairNumber = pairNumber;
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
