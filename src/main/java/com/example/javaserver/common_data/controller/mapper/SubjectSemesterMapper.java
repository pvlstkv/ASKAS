package com.example.javaserver.common_data.controller.mapper;

import com.example.javaserver.common_data.controller.dto.SubjectSemesterDto;
import com.example.javaserver.common_data.model.SubjectSemester;
import com.example.javaserver.study.controller.mapper.LiteratureIdMapper;
import com.example.javaserver.study.controller.mapper.TaskIdMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@Mapper(componentModel = "spring", uses = {
        TaskIdMapper.class,
        LiteratureIdMapper.class,
        SubjectIdMapper.class,
        StudyGroupIdMapper.class
})
public interface SubjectSemesterMapper {
    @Mappings({
            @Mapping(source = "tasks", target = "taskIds"),
            @Mapping(source = "literature", target = "literatureIds"),
            @Mapping(source = "subject", target = "subjectId"),
            @Mapping(source = "studyGroup", target = "studyGroupId")
    })
    SubjectSemesterDto toDto(final SubjectSemester subjectSemester);

    Collection<SubjectSemesterDto> toDto(final Collection<SubjectSemester> subjectSemesters);

    @Mappings({
            @Mapping(source = "subjectId", target = "subject"),
            @Mapping(source = "studyGroupId", target = "studyGroup")
    })
    SubjectSemester toEntity(final SubjectSemesterDto subjectSemesterDto);

    Collection<SubjectSemester> toEntity(final Collection<SubjectSemesterDto> subjectSemesterDto);
}
