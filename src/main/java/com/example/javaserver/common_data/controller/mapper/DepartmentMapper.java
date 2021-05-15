package com.example.javaserver.common_data.controller.mapper;

import com.example.javaserver.common_data.controller.dto.DepartmentDto;
import com.example.javaserver.common_data.model.Department;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValueCheckStrategy;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@Mapper(componentModel = "spring", uses = {
        FacultyIdMapper.class
})
public interface DepartmentMapper {
    @Mappings({
            @Mapping(source = "faculty", target = "facultyId")
    })
    DepartmentDto toDto(final Department department);

    Collection<DepartmentDto> toDto(final Collection<Department> departments);

    @Mappings({
            @Mapping(source = "facultyId", target = "faculty", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    })
    Department toEntity(final DepartmentDto DepartmentDto);

    Collection<Department> toEntity(final Collection<DepartmentDto> departmentDto);
}