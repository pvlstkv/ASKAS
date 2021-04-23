package com.example.javaserver.common_data.controller.mapper;

import com.example.javaserver.common_data.controller.dto.DepartmentDto;
import com.example.javaserver.common_data.controller.dto.FacultyDto;
import com.example.javaserver.common_data.model.Department;
import com.example.javaserver.common_data.model.Faculty;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValueCheckStrategy;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@Mapper(componentModel = "spring", uses = {
        DepartamentIdMapper.class
})
public interface FacultyMapper {
    @Mappings({
            @Mapping(source = "departments", target = "departmentIds")
    })
    FacultyDto toDto(final Faculty faculty);

    Collection<FacultyDto> toDto(final Collection<Faculty> faculties);

    @Mappings({
            @Mapping(source = "departmentIds", target = "departments", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    })
    Faculty toEntity(final FacultyDto facultyDto);
    Collection<Faculty> toEntity(final Collection<FacultyDto> facultyDto);
}
