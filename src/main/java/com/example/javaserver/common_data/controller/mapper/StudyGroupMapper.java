package com.example.javaserver.common_data.controller.mapper;

import com.example.javaserver.common_data.controller.dto.StudyGroupDto;
import com.example.javaserver.common_data.model.StudyGroup;
import com.example.javaserver.user.controller.mapper.UserIdMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValueCheckStrategy;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@Mapper(componentModel = "spring", uses = {
        DepartamentIdMapper.class,
        UserIdMapper.class,
        SemesterIdMapper.class
})
public interface StudyGroupMapper {
    @Mappings({
            @Mapping(source = "department", target = "departmentId"),
            @Mapping(source = "students", target = "studentIds"),
            @Mapping(source = "subjectSemesters", target = "subjectSemesterIds")
    })
    StudyGroupDto toDto(final StudyGroup studyGroup);

    Collection<StudyGroupDto> toDto(final Collection<StudyGroup> studyGroups);

    @Mappings({
            @Mapping(source = "departmentId", target = "department"),
            @Mapping(source = "studentIds", target = "students", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    })
    StudyGroup toEntity(final StudyGroupDto studyGroupDto);

    Collection<StudyGroup> toEntity(final Collection<StudyGroupDto> studyGroupDto);
}
