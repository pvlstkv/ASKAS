package com.example.javaserver.academic_performance.model;

import java.util.List;

public class TaskPerformance {
    private List<TaskPerformancePerUser> userList;
    private int passedCount;
    private double averageMark;

    public TaskPerformance() {
    }

    public TaskPerformance(List<TaskPerformancePerUser> userList, int passedCount, double averageMark) {
        this.userList = userList;
        this.passedCount = passedCount;
        this.averageMark = averageMark;
    }

    public List<TaskPerformancePerUser> getUserList() {
        return userList;
    }

    public void setUserList(List<TaskPerformancePerUser> userList) {
        this.userList = userList;
    }

    public int getPassedCount() {
        return passedCount;
    }

    public void setPassedCount(int passedCount) {
        this.passedCount = passedCount;
    }

    public double getAverageMark() {
        return averageMark;
    }

    public void setAverageMark(double averageMark) {
        this.averageMark = averageMark;
    }
}
