package com.example.javaserver.flex_mark.controller.dto;

import lombok.Data;

@Data
public class FMConfigPerCriteriaDto {

    private Long id;

    private Boolean isBinaryMark;

    private Integer passedValue;

    private Integer binaryBorder;

    private Integer fourFiveBorder;

    private Integer threeFourBorder;

    private Integer twoThreeBorder;
}
