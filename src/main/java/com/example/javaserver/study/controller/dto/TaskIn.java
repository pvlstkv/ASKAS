package com.example.javaserver.study.controller.dto;

import com.example.javaserver.study.model.TaskType;

import java.util.Set;

public class TaskIn {
    public Long id;
    public TaskType type;
    public String title;
    public String description;
    public Set<Long> semesterIds;
    public Set<Long> fileIds;
    public Set<Long> workIds;

    public TaskIn() { }
}
