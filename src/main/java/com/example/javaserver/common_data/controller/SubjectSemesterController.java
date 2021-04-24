package com.example.javaserver.common_data.controller;

import com.example.javaserver.common_data.controller.dto.SubjectSemesterDto;
import com.example.javaserver.common_data.controller.mapper.SubjectSemesterMapper;
import com.example.javaserver.common_data.model.SubjectSemester;
import com.example.javaserver.common_data.service.SubjectSemesterService;
import com.example.javaserver.general.criteria.SearchCriteria;
import com.example.javaserver.general.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Set;

@RestController
@RequestMapping("/subject-semester")
public class SubjectSemesterController {
    private final SubjectSemesterService subjectSemesterService;
    private final SubjectSemesterMapper subjectSemesterMapper;

    @Autowired
    public SubjectSemesterController(SubjectSemesterService subjectSemesterService, SubjectSemesterMapper subjectSemesterMapper) {
        this.subjectSemesterService = subjectSemesterService;
        this.subjectSemesterMapper = subjectSemesterMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Secured({"ADMIN"})
    public SubjectSemesterDto create(
            @RequestBody SubjectSemesterDto subjectSemesterDto
    ) {
        return subjectSemesterMapper.toDto(
                subjectSemesterService.create(
                        subjectSemesterMapper.toEntity(subjectSemesterDto)
                )
        );
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ADMIN"})
    public Message delete(
            @RequestBody Set<Long> ids
    ) {
        return subjectSemesterService.delete(ids);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ADMIN"})
    public Message update(
            @RequestParam("id") Long id,
            @RequestParam(value = "controlType", required = false) String controlType,
            @RequestParam(value = "hasCourseProject", required = false) String hasCourseProject,
            @RequestParam(value = "hasCourseWork", required = false) String hasCourseWork,
            @RequestParam(value = "subjectId", required = false) String subjectId
    ) {
        return subjectSemesterService.update(id, controlType, hasCourseProject, hasCourseWork, subjectId);
    }

    @PatchMapping("/assign")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ADMIN"})
    public Message setSubject(
            @RequestParam("subjectSemesterId") Long subjectSemesterId,
            @RequestParam("subjectId") Long subjectId
    ) {
        return subjectSemesterService.setSubject(subjectSemesterId, subjectId);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    public Collection<SubjectSemesterDto> search() {
        return subjectSemesterMapper.toDto(
                subjectSemesterService.getAll()
        );
    }

    @PostMapping("/criteria-search")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    public Collection<SubjectSemesterDto> criteriaSearch(
            @RequestBody Set<SearchCriteria> criteria
    ) {
        return subjectSemesterMapper.toDto(
                subjectSemesterService.criteriaSearch(criteria)
        );
    }

    @GetMapping("/search-by-ids")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    public Collection<SubjectSemesterDto> searchByIds(
            @RequestParam("ids") Set<Long> ids
    ) {
        return subjectSemesterMapper.toDto(
                subjectSemesterService.searchByIds(ids)
        );
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    public Collection<SubjectSemesterDto> searchBySubjectIdAndGroupIds(
            @RequestParam("subjectId") Long subjectId,
            @RequestParam("groupId") Set<Long> groupIds
    ) {
        return subjectSemesterMapper.toDto(
                subjectSemesterService.searchBySubjectIdAndGroupIds(subjectId, groupIds)
        );
    }
}
