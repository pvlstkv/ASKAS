package com.example.javaserver.action.controller.mapper;

import com.example.javaserver.action.controller.dto.ActionRequestDto;
import com.example.javaserver.action.controller.dto.ActionResponseDto;
import com.example.javaserver.action.model.Action;
import com.example.javaserver.user.controller.mapper.UserMapper;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(builder = @Builder(disableBuilder = true), componentModel = "spring", uses = UserMapper.class)
public interface ActionMapper {

    @Mapping(target = "users", ignore = true)
    Action toEntity(ActionRequestDto dto);

    ActionResponseDto toDto(Action action);
}
