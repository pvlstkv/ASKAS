package com.example.javaserver.common_data.controller.mapper;

import com.example.javaserver.common_data.controller.dto.SubjectDto;
import com.example.javaserver.common_data.model.Subject;
import com.example.javaserver.user.controller.mapper.UserIdMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValueCheckStrategy;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@Mapper(componentModel = "spring", uses = {
        //ThemeIdMapper.class,
        UserIdMapper.class,
        SemesterIdMapper.class,
        DepartamentIdMapper.class
        //QuestionIdMapper.class
})
public interface SubjectMapper {
    @Mappings({
            //@Mapping(source = "themes", target = "themeIds"),
            @Mapping(source = "teachers", target = "teacherIds"),
            @Mapping(source = "semesters", target = "semesterIds"),
            @Mapping(source = "department", target = "departmentId"),
            //@Mapping(source = "question", target = "questionIds")
    })
    SubjectDto toDto(final Subject subject);

    Collection<SubjectDto> toDto(final Collection<Subject> subjects);

    @Mappings({
            @Mapping(source = "teacherIds", target = "teachers", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS),
            @Mapping(source = "departmentId", target = "department")
    })
    Subject toEntity(final SubjectDto subjectDto);

    Collection<Subject> toEntity(final Collection<SubjectDto> subjectDto);
}
