package com.example.javaserver.flex_mark.controller.dto;

import lombok.Data;

@Data
public class FlexMarkDto {

    private Long id;

    private FMConfigPerCriteriaDto visit;

    private FMConfigPerCriteriaDto taskMark;

    private FMConfigPerCriteriaDto testMark;

    private Long teacherId;

    private Long subjectSemesterId;

    private Long studyGroupId;

}
