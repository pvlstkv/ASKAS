package com.example.javaserver.user.controller.mapper;


import com.example.javaserver.common_data.controller.mapper.DepartmentIdMapper;
import com.example.javaserver.common_data.controller.mapper.StudyGroupIdMapper;
import com.example.javaserver.study.controller.mapper.UserFileIdMapper;
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
        StudyGroupIdMapper.class,
        UserFileIdMapper.class,
        UserContactIdMapper.class
})
public interface UserMapper {
    @Mappings({
            @Mapping(source = "department", target = "departmentId"),
            @Mapping(source = "studyGroup", target = "studyGroupId"),
            @Mapping(source = "avatar", target = "avatarId"),
            @Mapping(source = "userContacts", target = "userContactIds")
    })
    UserDto toDto(final User user);

    Collection<UserDto> toDto(final Collection<User> users);

    @Mappings({
            @Mapping(source = "departmentId", target = "department"),
            @Mapping(source = "studyGroupId", target = "studyGroup", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS),
            @Mapping(source = "avatarId", target = "avatar", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS),
            @Mapping(source = "userContactIds", target = "userContacts", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    })
    User toEntity(final UserDto userDto);

    Collection<User> toEntity(final Collection<UserDto> userDto);
}
