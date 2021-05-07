package com.example.javaserver.study.controller;

import com.example.javaserver.general.criteria.SearchCriteria;
import com.example.javaserver.general.model.Message;
import com.example.javaserver.general.model.UserDetailsImp;
import com.example.javaserver.study.controller.dto.WorkDto;
import com.example.javaserver.study.controller.mapper.WorkMapper;
import com.example.javaserver.study.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Set;

@RestController
@RequestMapping("/work")
public class WorkController {
    private final WorkService workService;
    private final WorkMapper workMapper;

    @Autowired
    public WorkController(WorkService workService, WorkMapper workMapper) {
        this.workService = workService;
        this.workMapper = workMapper;
    }

    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    @PostMapping
    public WorkDto create(
            @RequestBody @Valid WorkDto workDto,
            @AuthenticationPrincipal UserDetailsImp userDetails
    ) {
        return workMapper.toDto(
                workService.create(
                        workMapper.toEntity(workDto),
                        userDetails
                )
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @Secured({ "TEACHER", "ADMIN"})
    @DeleteMapping
    public Message delete(
            @RequestBody Set<Long> ids
    ) {
        return workService.delete(ids);
    }

    @ResponseStatus(HttpStatus.OK)
    @Secured({ "TEACHER", "ADMIN"})
    @PutMapping
    public WorkDto update(
            @RequestParam("id") Long id,
            @RequestBody @Valid WorkDto workDto
    ) {
        return workMapper.toDto(
                workService.update(
                        id,
                        workMapper.toEntity(workDto)
                )
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    @GetMapping("/all")
    public Collection<WorkDto> getAll() {
        return workMapper.toDto(
                workService.getAll()
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    @PostMapping("/criteria-search")
    public Collection<WorkDto> criteriaSearch(
            @RequestBody Set<SearchCriteria> criteria
    ) {
        return workMapper.toDto(
                workService.criteriaSearch(criteria)
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    @GetMapping("/search-by-ids")
    public Collection<WorkDto> searchByIds(
            @RequestParam("ids") Set<Long> ids
    ) {
        return workMapper.toDto(
                workService.getByIds(ids)
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    @GetMapping("/teaching")
    public Collection<WorkDto> searchByGroupsAndTeacher(
            @RequestParam(value = "userId", required = false) Integer userId,
            @RequestParam("groupId") Long groupId,
            @AuthenticationPrincipal UserDetailsImp userDetails
    ) {
        return workMapper.toDto(
                workService.searchByGroupsAndTeacher(
                        userId,
                        groupId,
                        userDetails
                )
        );
    }
}
