package com.example.javaserver.conference.controller.mapper;

import com.example.javaserver.common_data.controller.mapper.StudyGroupIdMapper;
import com.example.javaserver.conference.model.Conference;
import com.example.javaserver.conference.controller.dto.ConferenceDto;
import com.example.javaserver.user.controller.mapper.UserIdMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValueCheckStrategy;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@Mapper(componentModel = "spring", uses = {
        StudyGroupIdMapper.class,
        UserIdMapper.class
})
public interface ConferenceMapper {
    @Mappings({
            @Mapping(source = "studyGroups", target = "studyGroupIds"),
            @Mapping(source = "user", target = "userId")
    })
    ConferenceDto toDto(final Conference conference);

    Collection<ConferenceDto> toDto(final Collection<Conference> conferences);

    @Mappings({
            @Mapping(source = "studyGroupIds", target = "studyGroups", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    })
    Conference toEntity(final ConferenceDto conferenceDto);
}
