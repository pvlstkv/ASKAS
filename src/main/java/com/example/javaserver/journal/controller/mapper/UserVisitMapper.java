package com.example.javaserver.journal.controller.mapper;

import com.example.javaserver.general.config.time_converter.DateMapper;
import com.example.javaserver.general.config.time_converter.MilliToOffsetDateTimeConverter;
import com.example.javaserver.general.config.time_converter.OffsetDateTimeToMilliConverter;
import com.example.javaserver.journal.controller.dto.UserVisitDto;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring", uses = {
        DateMapper.class,
        VisitMapper.class},
        unmappedTargetPolicy = ReportingPolicy.ERROR, builder = @Builder(disableBuilder = true)
)
@Component
public interface UserVisitMapper {
    @Mappings({
            @Mapping(source = "lessonDate", target = "lessonDate",
                    qualifiedBy = OffsetDateTimeToMilliConverter.class)
    })
    UserVisitDto toDto(UserVisit userVisit);

    List<UserVisitDto> toDtoList(List<UserVisit> list);
}
