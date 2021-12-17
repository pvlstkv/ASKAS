package com.example.javaserver.flex_mark.controller;

import com.example.javaserver.flex_mark.controller.dto.FlexMarkDto;
import com.example.javaserver.flex_mark.controller.mapper.FlexMarkMapper;
import com.example.javaserver.flex_mark.model.result.FlexMarkPerUser;
import com.example.javaserver.flex_mark.service.FMService;
import com.example.javaserver.general.model.UserDetailsImp;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/flex-mark")
@AllArgsConstructor
public class FMController {
    private FMService fmService;
    private FlexMarkMapper flexMarkMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Secured({"TEACHER", "ADMIN"})
    public void create(@RequestBody @Valid FlexMarkDto flexMarkDto,
                       @AuthenticationPrincipal UserDetailsImp userDetails
    ) {
        fmService.save(
                flexMarkMapper.toEntity(flexMarkDto), userDetails
        );
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Secured({"TEACHER", "ADMIN"})
    public void update(@RequestBody @Valid FlexMarkDto flexMarkDto,
                       @AuthenticationPrincipal UserDetailsImp userDetails
    ) {
        fmService.update(flexMarkMapper.toEntity(flexMarkDto));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Secured({"TEACHER", "ADMIN"})
    public FlexMarkDto getByIds(@RequestParam Integer teacherId,
                                @RequestParam Long subjectSemesterId,
                                @RequestParam Long studyGroupId) {
        return flexMarkMapper.toDto(
                fmService.getConfig(teacherId, subjectSemesterId, studyGroupId)
        );
    }

    @GetMapping("/per-user")
    @ResponseStatus(HttpStatus.CREATED)
    @Secured({"TEACHER", "ADMIN", "USER"})
    public FlexMarkPerUser getFMPerUser(@RequestParam Integer studentId,
                                        @RequestParam Long subjectSemesterId) {
        return fmService.formFlexMark(studentId, subjectSemesterId);
    }

}
