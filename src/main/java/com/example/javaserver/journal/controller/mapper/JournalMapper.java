package com.example.javaserver.journal.controller.mapper;

import com.example.javaserver.common_data.controller.mapper.StudyGroupIdMapper;
import com.example.javaserver.common_data.controller.mapper.SubjectSemesterIdMapper;
import com.example.javaserver.general.config.time_converter.DateMapper;
import com.example.javaserver.general.config.time_converter.MilliToOffsetDateTimeConverter;
import com.example.javaserver.general.config.time_converter.OffsetDateTimeToMilliConverter;
import com.example.javaserver.journal.controller.dto.JournalDto;
import com.example.javaserver.journal.model.Journal;
import com.example.javaserver.user.controller.mapper.UserIdMapper;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring", uses = {
        UserIdMapper.class,
        SubjectSemesterIdMapper.class,
        StudyGroupIdMapper.class,
        VisitMapper.class,
        DateMapper.class},
        unmappedTargetPolicy = ReportingPolicy.ERROR, builder = @Builder(disableBuilder = true)
)
@Component
public interface JournalMapper {
    @Mappings({
            @Mapping(source = "subjectSemesterId", target = "subjectSemester"),
            @Mapping(source = "studyGroupId", target = "studyGroup"),
            @Mapping(source = "teacherId", target = "teacher"),
            @Mapping(target = "createdBy", ignore = true),
            @Mapping(target = "lastModifiedBy", ignore = true),
            @Mapping(target = "createdDate", ignore = true),
            @Mapping(target = "lastModifiedDate", ignore = true),
            @Mapping(source = "lessonDate", target = "lessonDate",
                    qualifiedBy = MilliToOffsetDateTimeConverter.class)

    })
    Journal toEntity(JournalDto journalDto);

    @AfterMapping
    default void afterToEntityJournalMapping(@MappingTarget Journal journal) {
        journal.getVisits().forEach(visit -> visit.setJournal(journal));
    }


    List<JournalDto> toDtoList(List<Journal> journal);

    @Mappings({
            @Mapping(source = "subjectSemester", target = "subjectSemesterId"),
            @Mapping(source = "studyGroup", target = "studyGroupId"),
            @Mapping(source = "teacher", target = "teacherId"),
            @Mapping(source = "createdDate", target = "createdDate",
                    qualifiedBy = OffsetDateTimeToMilliConverter.class),
            @Mapping(source = "lastModifiedDate", target = "lastModifiedDate",
                    qualifiedBy = OffsetDateTimeToMilliConverter.class),
            @Mapping(source = "lessonDate", target = "lessonDate",
                    qualifiedBy = OffsetDateTimeToMilliConverter.class)

    })
    JournalDto toDto(Journal journal);
}
