package com.example.javaserver.study.controller.mapper;

import com.example.javaserver.study.controller.dto.UserFileDto;
import com.example.javaserver.study.model.UserFile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring"/*, uses = { UserRepo.class }*/)
public interface UserFileMapper {
    @Mappings({
            @Mapping(source = "user.id", target = "userId")
    })
    UserFileDto toDto(final UserFile userFile);

    /*@Mappings({
            @Mapping(source = "userId", target = "user", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    })*/
    UserFile toEntity(final UserFileDto userFileDto);
}
