package com.example.javaserver.study.controller.mapper;

import com.example.javaserver.common_data.controller.mapper.SemesterIdMapper;
import com.example.javaserver.study.controller.dto.TaskDto;
import com.example.javaserver.study.model.Task;
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
        WorkIdMapper.class,
        UserIdMapper.class
})
public interface TaskMapper {
    @Mappings({
            @Mapping(source = "userFiles", target = "fileIds"),
            @Mapping(source = "semesters", target = "semesterIds"),
            @Mapping(source = "works", target = "workIds"),
            @Mapping(source = "user", target = "userId")
    })
    TaskDto toDto(final Task task);

    Collection<TaskDto> toDto(final Collection<Task> tasks);

    @Mappings({
            @Mapping(source = "fileIds", target = "userFiles", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS),
            @Mapping(source = "semesterIds", target = "semesters", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    })
    Task toEntity(final TaskDto taskDto);

    Collection<Task> toEntity(final Collection<TaskDto> taskDtos);
}
