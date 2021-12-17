package com.example.javaserver.flex_mark.controller.mapper;

import com.example.javaserver.common_data.controller.mapper.StudyGroupIdMapper;
import com.example.javaserver.common_data.controller.mapper.SubjectSemesterIdMapper;
import com.example.javaserver.flex_mark.controller.dto.FlexMarkDto;
import com.example.javaserver.flex_mark.model.FlexMark;
import com.example.javaserver.user.controller.mapper.UserIdMapper;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", uses = {
        UserIdMapper.class,
        SubjectSemesterIdMapper.class,
        StudyGroupIdMapper.class,
        FMConfigPerCriteriaMapper.class},
        unmappedTargetPolicy = ReportingPolicy.ERROR, builder = @Builder(disableBuilder = true)
)
@Component
public interface FlexMarkMapper {
    @Mappings({
            @Mapping(source = "teacherId", target = "teacher"),
            @Mapping(source = "subjectSemesterId", target="subjectSemester"),
            @Mapping(source = "studyGroupId", target="studyGroup")
    })
    FlexMark toEntity(FlexMarkDto flexMarkDto);

    @Mappings({
            @Mapping(source = "teacher", target = "teacherId"),
            @Mapping(source = "subjectSemester", target="subjectSemesterId"),
            @Mapping(source = "studyGroup", target="studyGroupId")
    })
    FlexMarkDto toDto(FlexMark flexMark);

    @Mappings({
            @Mapping(source = "teacher", target = "teacher"),
            @Mapping(source = "subjectSemester", target="subjectSemester"),
            @Mapping(source = "studyGroup", target="studyGroup")
    })
    FlexMark entityToEntity(FlexMark flexMark);
}
