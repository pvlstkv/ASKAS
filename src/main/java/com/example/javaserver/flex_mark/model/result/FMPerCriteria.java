package com.example.javaserver.flex_mark.model.result;

import lombok.Data;

import java.util.List;

@Data
public class FMPerCriteria {

    private Integer done;

    private Integer total;

    private Integer mark;

    private List<Long> ids;
}
