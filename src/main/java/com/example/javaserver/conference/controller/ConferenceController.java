package com.example.javaserver.conference.controller;

import com.example.javaserver.conference.controller.dto.ConferenceDto;
import com.example.javaserver.conference.controller.mapper.ConferenceMapper;
import com.example.javaserver.conference.service.ConferenceService;
import com.example.javaserver.general.model.UserDetailsImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Set;

@RequestMapping("/conferences")
@RestController
public class ConferenceController {
    private final ConferenceService conferenceService;
    private final ConferenceMapper conferenceMapper;

    @Autowired
    public ConferenceController(ConferenceService conferenceService, ConferenceMapper conferenceMapper) {
        this.conferenceService = conferenceService;
        this.conferenceMapper = conferenceMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Secured({ "TEACHER", "ADMIN" })
    public ConferenceDto create(
            @RequestBody @Valid ConferenceDto conferenceDto,
            @AuthenticationPrincipal UserDetailsImp userDetails
    ) {
        return conferenceMapper.toDto(
                conferenceService.create(
                        conferenceMapper.toEntity(conferenceDto),
                        userDetails
                )
        );
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    @Secured({ "TEACHER", "ADMIN" })
    public ConferenceDto delete(
            @RequestParam("id") Long id
    ) {
        return conferenceMapper.toDto(
                conferenceService.delete(id)
        );
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @Secured({ "TEACHER", "ADMIN" })
    public ConferenceDto update(
            @RequestParam("id") Long id,
            @RequestBody @Valid ConferenceDto conferenceDto
    ) {
        return conferenceMapper.toDto(
                conferenceService.update(
                        id,
                        conferenceMapper.toEntity(conferenceDto)
                )
        );
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    @Secured({ "ADMIN" })
    public Collection<ConferenceDto> getAll() {
        return conferenceMapper.toDto(
                conferenceService.getAll()
        );
    }

    @GetMapping("/search-by-ids")
    @ResponseStatus(HttpStatus.OK)
    @Secured({ "ADMIN" })
    public Collection<ConferenceDto> getAll(
            @RequestParam("ids") Set<Long> ids
    ) {
        return conferenceMapper.toDto(
                conferenceService.getByIds(ids)
        );
    }

    @GetMapping("/learning")
    @ResponseStatus(HttpStatus.OK)
    @Secured({ "USER", "TEACHER", "ADMIN" })
    public Collection<ConferenceDto> getAll(
            @AuthenticationPrincipal UserDetailsImp userDetails
    ) {
        return conferenceMapper.toDto(
                conferenceService.getByStudentId(userDetails)
        );
    }

    @GetMapping("/search-by-student")
    @ResponseStatus(HttpStatus.OK)
    @Secured({ "TEACHER", "ADMIN" })
    public Collection<ConferenceDto> getAll(
            @RequestParam(value = "studentId") Integer studentId
    ) {
        return conferenceMapper.toDto(
                conferenceService.getByStudentId(studentId)
        );
    }
}
