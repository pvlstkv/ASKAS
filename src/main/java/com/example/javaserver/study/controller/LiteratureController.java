package com.example.javaserver.study.controller;

import com.example.javaserver.general.criteria.SearchCriteria;
import com.example.javaserver.general.model.Message;
import com.example.javaserver.general.model.UserDetailsImp;
import com.example.javaserver.study.controller.dto.LiteratureDto;
import com.example.javaserver.study.controller.mapper.LiteratureMapper;
import com.example.javaserver.study.service.LiteratureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Set;

@RestController
@RequestMapping("/literature")
public class LiteratureController {
    private final LiteratureService literatureService;
    private final LiteratureMapper literatureMapper;

    @Autowired
    public LiteratureController(LiteratureService literatureService, LiteratureMapper literatureMapper) {
        this.literatureService = literatureService;
        this.literatureMapper = literatureMapper;
    }

    @ResponseStatus(HttpStatus.OK)
    @Secured({"TEACHER", "ADMIN"})
    @PostMapping
    public LiteratureDto create(
            @RequestBody @Valid LiteratureDto literatureDto,
            @AuthenticationPrincipal UserDetailsImp userDetails
    ) {
        return literatureMapper.toDto(
                literatureService.create(
                        literatureMapper.toEntity(literatureDto),
                        userDetails
                )
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @Secured({"TEACHER", "ADMIN"})
    @DeleteMapping
    public Message delete(
            @RequestBody Set<Long> ids
    ) {
        return literatureService.delete(ids);
    }

    @ResponseStatus(HttpStatus.OK)
    @Secured({"TEACHER", "ADMIN"})
    @PutMapping
    public LiteratureDto update(
            @RequestParam("id") Long id,
            @RequestBody @Valid LiteratureDto literatureDto
    ) {
        return literatureMapper.toDto(
                literatureService.update(
                        id,
                        literatureMapper.toEntity(literatureDto)
                )
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    @GetMapping("/all")
    public Collection<LiteratureDto> getAll() {
        return literatureMapper.toDto(
                literatureService.getAll()
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    @PostMapping("/criteria-search")
    public Collection<LiteratureDto> criteriaSearch(
            @RequestBody Set<SearchCriteria> criteria
    ) {
        return literatureMapper.toDto(
                literatureService.criteriaSearch(criteria)
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    @GetMapping("/search-by-ids")
    public Collection<LiteratureDto> searchByIds(
            @RequestParam("ids") Set<Long> ids
    ) {
        return literatureMapper.toDto(
                literatureService.getByIds(ids)
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    @GetMapping("/learning")
    public Collection<LiteratureDto> searchBySubjectAndStudent(
            @RequestParam("subjectId") Long subjectId,
            @RequestParam(value = "studentId", required = false) Integer userId,
            @AuthenticationPrincipal UserDetailsImp userDetails
    ) {
        return literatureMapper.toDto(
                literatureService.searchBySubjectAndStudent(
                        subjectId,
                        userId,
                        userDetails
                )
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    @GetMapping
    public Collection<LiteratureDto> searchBySubject(
            @RequestParam("subjectId") Long subjectId
    ) {
        return literatureMapper.toDto(
                literatureService.searchBySubject(subjectId)
        );
    }
}
