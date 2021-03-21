package com.example.javaserver.study.controller.dto;

import com.example.javaserver.study.model.TaskType;

import java.util.Set;

public class TaskIn {
    public TaskType type;
    public String title;
    public String description;
    public Long semesterId;
    public Set<Long> fileIds;

    public TaskIn() { }
}
