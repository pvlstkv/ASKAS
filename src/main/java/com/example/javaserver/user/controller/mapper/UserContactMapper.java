package com.example.javaserver.user.controller.mapper;

import com.example.javaserver.user.controller.dto.UserContactDto;
import com.example.javaserver.user.model.UserContact;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValueCheckStrategy;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@Mapper(componentModel = "spring", uses = {
        UserIdMapper.class
})
public interface UserContactMapper {
    @Mappings({
            @Mapping(source = "user", target = "userId")
    })
    UserContactDto toDto(final UserContact userContact);

    Collection<UserContactDto> toDto(final Collection<UserContact> userContacts);

    @Mappings({
            @Mapping(source = "userId", target = "user", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    })
    UserContact toEntity(final UserContactDto userContactDto);

    Collection<UserContact> toEntity(final Collection<UserContactDto> userContactDtos);
}
