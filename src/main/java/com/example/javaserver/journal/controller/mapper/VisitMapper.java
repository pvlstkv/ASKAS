package com.example.javaserver.journal.controller.mapper;

import com.example.javaserver.journal.controller.dto.VisitDto;
import com.example.javaserver.journal.model.Visit;
import com.example.javaserver.user.controller.mapper.UserIdMapper;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring",uses = UserIdMapper.class,
        unmappedTargetPolicy = ReportingPolicy.ERROR, builder = @Builder(disableBuilder = true)
)
@Component
public interface VisitMapper {
    @Mappings({
            @Mapping(source = "user", target = "studentId")
    })
    VisitDto toDto(Visit visit);

    @Mappings({
            @Mapping(source = "studentId", target = "user"),
            @Mapping(target = "journal", ignore = true)
    })
    Visit toEntity(VisitDto visitDto);
}
