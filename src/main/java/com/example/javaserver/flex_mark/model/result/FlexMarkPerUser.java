package com.example.javaserver.flex_mark.model.result;

import lombok.Data;

@Data
public class FlexMarkPerUser {

    private int studentId;

    private String firstName;

    private String lastName;

    private String patronymic;

    private FMPerCriteria visitMark;

    private FMPerCriteria taskMark;

    private FMPerCriteria testMark;

    private int resultMark;

}
