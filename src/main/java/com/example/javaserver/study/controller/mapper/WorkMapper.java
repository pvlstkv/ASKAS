package com.example.javaserver.study.controller.mapper;

import com.example.javaserver.study.controller.dto.WorkDto;
import com.example.javaserver.study.model.Work;
import com.example.javaserver.user.controller.mapper.UserIdMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValueCheckStrategy;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@Mapper(componentModel = "spring", uses = {
        TaskIdMapper.class,
        UserIdMapper.class,
        UserFileIdMapper.class
})
public interface WorkMapper {
    @Mappings({
            @Mapping(source = "task", target = "taskId"),
            @Mapping(source = "user", target = "userId"),
            @Mapping(source = "userFiles", target = "fileIds")
    })
    WorkDto toDto(final Work work);

    Collection<WorkDto> toDto(final Collection<Work> works);

    @Mappings({
            @Mapping(source = "taskId", target = "task", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS),
            @Mapping(source = "fileIds", target = "userFiles", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    })
    Work toEntity(final WorkDto workDto);

    Collection<Work> toEntity(final Collection<WorkDto> workDtos);
}
