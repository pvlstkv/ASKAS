package com.example.javaserver.common_data.controller.client_model;

import com.example.javaserver.common_data.model.SubjectControlType;

@SuppressWarnings("unused")
public class SubjectSemesterIn {
    public SubjectControlType controlType;
    public Boolean hasCourseProject;
    public Boolean hasCourseWork;
    public Integer numberOfSemester;
    public Long subjectId;

    public SubjectSemesterIn() { }
}
