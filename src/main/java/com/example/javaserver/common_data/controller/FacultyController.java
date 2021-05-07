package com.example.javaserver.common_data.controller;

import com.example.javaserver.common_data.controller.dto.FacultyDto;
import com.example.javaserver.common_data.controller.mapper.FacultyMapper;
import com.example.javaserver.common_data.model.Faculty;
import com.example.javaserver.common_data.service.FacultyService;
import com.example.javaserver.general.criteria.SearchCriteria;
import com.example.javaserver.general.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Set;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService facultyService;
    private final FacultyMapper facultyMapper;

    @Autowired
    public FacultyController(FacultyService facultyService, FacultyMapper facultyMapper) {
        this.facultyService = facultyService;
        this.facultyMapper = facultyMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Secured({"ADMIN"})
    public FacultyDto create(
            @RequestBody FacultyDto facultyDto
    ) {
        return facultyMapper.toDto(
                facultyService.create(
                        facultyMapper.toEntity(facultyDto))
        );
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ADMIN"})
    public Message delete(
            @RequestBody Set<Long> ids
    ) {
        return facultyService.delete(ids);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ADMIN"})
    public Message update(
            @RequestParam("id") Long id,
            @RequestParam(value = "shortName", required = false) String shortName,
            @RequestParam(value = "fullName", required = false) String fullName
    ) {
        return facultyService.update(id, shortName, fullName);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    public Collection<FacultyDto> getAll() {
        return facultyMapper.toDto(
                facultyService.getAll()
        );
    }

    @PostMapping("/criteria-search")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    public Collection<FacultyDto> criteriaSearch(
            @RequestBody Set<SearchCriteria> criteria
    ) {
        return facultyMapper.toDto(
                facultyService.criteriaSearch(criteria)
        );
    }

    @GetMapping("/search-by-ids")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    public Collection<FacultyDto> searchByIds(
            @RequestParam("ids") Set<Long> ids
    ) {
        return facultyMapper.toDto(
                facultyService.searchByIds(ids)
        );
    }
}
