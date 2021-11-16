package com.example.javaserver.journal.controller.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Api(value = "data transfer object for the visit, VisitDto is a class responsible for one visit by one person")
@Data
public class VisitDto {

    @ApiModelProperty(notes = "The database generated journal ID. Required only for updating")
    private Long id;

    @ApiModelProperty(notes = "Id of a student from a certain study group")
    @NotNull
    @NotNull(message = "studentId may not be null")
    private Long studentId;

    @ApiModelProperty(notes = "A boolean flag responsing for visiting a pair or not")
    @NotNull
    @NotNull(message = "isVisited may not be null")
    private Boolean isVisited;

    @ApiModelProperty(notes = "Any teacher comment")
    private String comment;

    @ApiModelProperty(notes = "A date of creating journal")
    private long createdDate;

    @ApiModelProperty(notes = "A date of updating journal")
    private long lastModifiedDate;
}
