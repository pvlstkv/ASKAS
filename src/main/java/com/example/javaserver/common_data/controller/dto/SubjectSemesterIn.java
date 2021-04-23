package com.example.javaserver.common_data.controller.dto;

import com.example.javaserver.common_data.model.SubjectControlType;

@SuppressWarnings("unused")
public class SubjectSemesterIn {
    public SubjectControlType controlType;
    public Boolean hasCourseProject;
    public Boolean hasCourseWork;
    public Long subjectId;


    public SubjectSemesterIn() { }
}
