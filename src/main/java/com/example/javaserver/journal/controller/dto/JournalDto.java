package com.example.javaserver.journal.controller.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;


import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Collection;

@Api(value = "Data transfer object for the journal. JournalDto is a class responsible for visiting by one group")
@Data
public class JournalDto {

    @ApiModelProperty(notes = "The database generated journal ID. Required only for updating")
    private Long id;

    @ApiModelProperty(notes = "Any teacher comment")
    private String comment;

    @ApiModelProperty(notes = "Id of a subject semester")
    @NotNull(message = "subjectSemesterId may not be null")
    private Long subjectSemesterId;

    @ApiModelProperty(notes = "Id of a study group")
    @NotNull(message = "studyGroupId may not be null")
    private Long studyGroupId;

    @ApiModelProperty(notes = "A list of VisitDto objects")
    @NotNull
    @NotNull(message = "visits may not be null")
    private Collection<VisitDto> visits;

    @ApiModelProperty(notes = "Id of a filling out teacher")
    @NotNull
    @NotNull(message = "teacherId may not be null")
    private Long teacherId;
}
