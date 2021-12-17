package com.example.javaserver.flex_mark.model.result;

import lombok.Data;

@Data
public class FlexMarkPerUser {

    private FMPerCriteria visit;

    private FMPerCriteria taskOnTime;

    private FMPerCriteria taskMark;

    private FMPerCriteria testMark;

}
