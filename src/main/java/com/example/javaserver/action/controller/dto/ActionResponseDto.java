package com.example.javaserver.action.controller.dto;

import com.example.javaserver.action.model.ActionType;
import com.example.javaserver.user.controller.dto.UserDto;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import java.time.LocalDate;
import java.util.Set;

@Data
public class ActionResponseDto {
    @ApiParam(name = "id")
    private Long id;

    @ApiParam(name = "title")
    private String title;

    @ApiParam(name = "description")
    private String description;

    @ApiParam(name = "actionDate")
    private LocalDate actionDate;

    @ApiParam(name = "ActionType")
    private ActionType actionType;

    @ApiParam(name = "users")
    private Set<UserDto> users;
}
