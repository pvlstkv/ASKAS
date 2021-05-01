package com.example.javaserver.common_data.controller;

import com.example.javaserver.common_data.controller.dto.StudyGroupDto;
import com.example.javaserver.common_data.controller.mapper.StudyGroupMapper;
import com.example.javaserver.common_data.model.StudyGroup;
import com.example.javaserver.common_data.service.StudyGroupService;
import com.example.javaserver.general.model.Message;
import com.example.javaserver.general.model.UserDetailsImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Set;

@RestController
@RequestMapping("/study-group")
public class StudyGroupController {
    private final StudyGroupService studyGroupService;
    private final StudyGroupMapper studyGroupMapper;

    @Autowired
    public StudyGroupController(StudyGroupService studyGroupService, StudyGroupMapper studyGroupMapper) {
        this.studyGroupService = studyGroupService;
        this.studyGroupMapper = studyGroupMapper;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    public StudyGroupDto getGroup(
            @RequestParam(required = false) Long id
    ){
        return studyGroupMapper.toDto(
                studyGroupService.getById(id)
        );
    }

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public Collection<StudyGroupDto> getListGroupStudy(){
        return studyGroupMapper.toDto(
                studyGroupService.getAll()
        );
    }

    @GetMapping("/search-by-ids")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    public Collection<StudyGroupDto> searchByIds(
            @RequestParam("id") Set<Long> ids
    ) {
        return studyGroupMapper.toDto(
                studyGroupService.getByIds(ids)
        );
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Secured({"ADMIN"})
    public StudyGroupDto create(
            @RequestBody StudyGroupDto studyGroupDto
    ) {
        return studyGroupMapper.toDto(
                studyGroupService.create(
                        studyGroupMapper.toEntity(studyGroupDto)
                )
        );
    }

    @PostMapping("/enrollment")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ADMIN"})
    public Message create(
            @RequestParam("study_group_id") Long studyGroupId,
            @RequestBody Set<Integer> userIds
    ) {
        return studyGroupService.enroll(studyGroupId, userIds);
    }

    @PatchMapping("/subject_semester_addition")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ADMIN"})
    public Message addSubjectSemesters(
            @RequestParam("id") Long studyGroupId,
            @RequestBody Set<Long> subjectSemesterIds
    ) {
        return studyGroupService.addSubjectSemesters(studyGroupId, subjectSemesterIds);
    }

    @GetMapping("/teaching")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    public Collection<StudyGroupDto> getGroupsByTeacher(
            @RequestParam(value = "userId", required = false) Integer userId,
            @AuthenticationPrincipal UserDetailsImp userDetails
    ){
        return studyGroupMapper.toDto(
                studyGroupService.getGroupsByTeacher(userId, userDetails)
        );
    }

    @GetMapping("/learning")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    public Collection<StudyGroupDto> getGroupsByTeacher(
            @RequestParam("subjectId") Long subjectId
    ){
        return studyGroupMapper.toDto(
                studyGroupService.getGroupsBySubject(subjectId)
        );
    }
}
