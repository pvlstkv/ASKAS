package com.example.javaserver.study.controller.dto;

import com.example.javaserver.common_data.model.Mark;

import java.util.Set;

public class WorkIn {
    public Long id;
    public Long taskId;
    public Set<Long> fileIds;
    public String teacherComment;
    public String studentComment;
    public Mark mark;

    public WorkIn() { }
}
