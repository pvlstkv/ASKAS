package com.example.javaserver.journal.controller.mapper;

import com.example.javaserver.general.config.time_converter.DateMapper;
import com.example.javaserver.general.config.time_converter.OffsetDateTimeConverter;
import com.example.javaserver.journal.controller.dto.VisitDto;
import com.example.javaserver.journal.model.Visit;
import com.example.javaserver.user.controller.mapper.UserIdMapper;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", uses = {
        UserIdMapper.class,
        DateMapper.class},
        unmappedTargetPolicy = ReportingPolicy.ERROR, builder = @Builder(disableBuilder = true)
)
@Component
public interface VisitMapper {
    @Mappings({
            @Mapping(source = "user", target = "studentId"),
            @Mapping(source = "createdDate", target = "createdDate",
                    qualifiedBy = OffsetDateTimeConverter.class),
            @Mapping(source = "lastModifiedDate", target = "lastModifiedDate",
                    qualifiedBy = OffsetDateTimeConverter.class)
    })
    VisitDto toDto(Visit visit);

    @Mappings({
            @Mapping(source = "studentId", target = "user"),
            @Mapping(target = "journal", ignore = true),
            @Mapping(target = "createdBy", ignore = true),
            @Mapping(target = "lastModifiedBy", ignore = true),
            @Mapping(target = "createdDate", ignore = true),
            @Mapping(target = "lastModifiedDate", ignore = true),
    })
    Visit toEntity(VisitDto visitDto);
}
