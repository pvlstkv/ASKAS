package com.example.javaserver.action.controller.dto;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import java.time.LocalDate;

@Data
public class ActionRequestDto {

    @ApiParam(name = "title")
    private String title;

    @ApiParam(name = "description")
    private String description;

    @ApiParam(name = "actionDate")
    private LocalDate actionDate;

    @ApiParam(name = "ActionTypeId")
    private Long actionTypeId;

}
