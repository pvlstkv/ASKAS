package com.example.javaserver.common_data.controller;

import com.example.javaserver.common_data.controller.dto.SubjectDto;
import com.example.javaserver.common_data.controller.mapper.SubjectMapper;
import com.example.javaserver.common_data.model.Subject;
import com.example.javaserver.common_data.service.SubjectService;
import com.example.javaserver.general.criteria.SearchCriteria;
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
@RequestMapping("/subject")
public class SubjectController {
    private final SubjectService subjectService;
    private final SubjectMapper subjectMapper;

    @Autowired
    public SubjectController(SubjectService subjectService, SubjectMapper subjectMapper) {
        this.subjectService = subjectService;
        this.subjectMapper = subjectMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Secured({"ADMIN"})
    public SubjectDto create(
            @RequestBody SubjectDto subjectDto
    ) {
        return subjectMapper.toDto(
                subjectService.create(
                        subjectMapper.toEntity(subjectDto)
                )
        );
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ADMIN"})
    public Message delete(
            @RequestBody Set<Long> ids
    ) {
        return subjectService.delete(ids);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ADMIN"})
    public Message update(
            @RequestParam("id") Long id,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "decryption", required = false) String decryption,
            @RequestParam(value = "departmentId", required = false) String departmentId
    ) {
        return subjectService.update(id, name, decryption, departmentId);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    public Collection<SubjectDto> search() {
        return subjectMapper.toDto(
                subjectService.getAll()
        );
    }

    @PostMapping("/criteria-search")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    public Collection<SubjectDto> criteriaSearch(
            @RequestBody Set<SearchCriteria> criteria
    ) {
        return subjectMapper.toDto(
                subjectService.criteriaSearch(criteria)
        );
    }

    @GetMapping("/search-by-ids")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    public Collection<SubjectDto> searchByIds(
            @RequestParam("ids") Set<Long> ids
    ) {
        return subjectMapper.toDto(
             subjectService.getByIds(ids)
        );
    }

    @GetMapping("/learning")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    public Collection<SubjectDto> searchByStudentId(
            @RequestParam(value = "userId", required = false) Integer userId,
            @AuthenticationPrincipal UserDetailsImp userDetails
    ) {
        return subjectMapper.toDto(
                subjectService.searchByStudentId(userId, userDetails)
        );
    }

    @GetMapping("/teaching")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    public Collection<SubjectDto> searchByTeacherId(
            @RequestParam(value = "userId", required = false) Integer userId,
            @AuthenticationPrincipal UserDetailsImp userDetails
    ) {
        return subjectMapper.toDto(
                subjectService.searchByTeacherId(userId, userDetails)
        );
    }

    @PostMapping("/teacher-adding")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ADMIN"})
    public Message addTeachers(
            @RequestParam("subjectId") Long subjectId,
            @RequestParam("userId") Set<Integer> userIds
    ){
        return subjectService.addTeachers(subjectId, userIds);
    }
}
