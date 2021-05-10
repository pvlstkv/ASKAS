package com.example.javaserver.user.controller.mapper;


import com.example.javaserver.common_data.controller.dto.StudyGroupDto;
import com.example.javaserver.common_data.controller.mapper.DepartmentIdMapper;
import com.example.javaserver.common_data.controller.mapper.SemesterIdMapper;
import com.example.javaserver.common_data.controller.mapper.StudyGroupIdMapper;
import com.example.javaserver.common_data.model.StudyGroup;
import com.example.javaserver.user.controller.dto.UserDto;
import com.example.javaserver.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValueCheckStrategy;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@Mapper(componentModel = "spring", uses = {
        DepartmentIdMapper.class,
        StudyGroupIdMapper.class
})
public interface UserMapper {
    @Mappings({
            @Mapping(source = "department", target = "departmentId"),
            @Mapping(source = "studyGroup", target = "studyGroupId"),
    })
    UserDto toDto(final User user);

    Collection<UserDto> toDto(final Collection<User> users);

    @Mappings({
            @Mapping(source = "departmentId", target = "department"),
            @Mapping(source = "studyGroupId", target = "studyGroup", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    })
    User toEntity(final UserDto userDto);

    Collection<User> toEntity(final Collection<UserDto> userDto);
}
