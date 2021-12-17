package com.example.javaserver.flex_mark.controller.mapper;

import com.example.javaserver.flex_mark.controller.dto.FMConfigPerCriteriaDto;
import com.example.javaserver.flex_mark.model.FMConfigPerCriteria;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR, builder = @Builder(disableBuilder = true)
)
@Component
public interface FMConfigPerCriteriaMapper {

    FMConfigPerCriteria toEntity(FMConfigPerCriteriaDto fmConfigPerCriteriaDto);

    FMConfigPerCriteriaDto toDto(FMConfigPerCriteria fmConfigPerCriteria);
}
