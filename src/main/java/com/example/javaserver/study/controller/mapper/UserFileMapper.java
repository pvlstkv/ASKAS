package com.example.javaserver.study.controller.mapper;

import com.example.javaserver.study.controller.dto.UserFileDto;
import com.example.javaserver.study.model.UserFile;
import com.example.javaserver.user.controller.mapper.UserIdMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@Mapper(componentModel = "spring", uses = { UserIdMapper.class })
public interface UserFileMapper {
    @Mappings({
            @Mapping(source = "user", target = "userId")
    })
    UserFileDto toDto(final UserFile userFile);

    Collection<UserFileDto> toDto(final Collection<UserFile> userFiles);

    UserFile toEntity(final UserFileDto userFileDto);

    Collection<UserFile> toEntity(final Collection<UserFileDto> userFileDtos);
}
