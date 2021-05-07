package com.example.javaserver.study.controller.mapper;

import com.example.javaserver.common_data.controller.mapper.SemesterIdMapper;
import com.example.javaserver.study.controller.dto.LiteratureDto;
import com.example.javaserver.study.model.Literature;
import com.example.javaserver.user.controller.mapper.UserIdMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValueCheckStrategy;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@Mapper(componentModel = "spring", uses = {
        UserFileIdMapper.class,
        SemesterIdMapper.class,
        UserIdMapper.class
})
public interface LiteratureMapper {
    @Mappings({
            @Mapping(source = "files", target = "fileIds"),
            @Mapping(source = "semesters", target = "semesterIds"),
            @Mapping(source = "user", target = "userId")
    })
    LiteratureDto toDto(final Literature literature);

    Collection<LiteratureDto> toDto(final Collection<Literature> literature);

    @Mappings({
            @Mapping(source = "fileIds", target = "files", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS),
            @Mapping(source = "semesterIds", target = "semesters", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    })
    Literature toEntity(final LiteratureDto literatureDto);

    Collection<Literature> toEntity(final Collection<LiteratureDto> literatureDtos);
}
