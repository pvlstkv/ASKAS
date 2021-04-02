package com.example.javaserver.common_data.controller;

import com.example.javaserver.common_data.controller.client_model.FacultyIn;
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

    @Autowired
    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Secured({"ADMIN"})
    public Message create(
            @RequestBody FacultyIn facultyIn
    ) {
        return facultyService.create(facultyIn);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ADMIN"})
    public Message delete(
            @RequestBody Set<Long> ids
    ) {
        return facultyService.delete(ids);
    }

    @PatchMapping
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
    public Collection<Faculty> getAll() {
        return facultyService.getAll();
    }

    @PostMapping("/criteria-search")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    public Collection<Faculty> criteriaSearch(
            @RequestBody Set<SearchCriteria> criteria
    ) {
        return facultyService.criteriaSearch(criteria);
    }

    @PostMapping("/search-by-ids")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    public Collection<Faculty> searchByIds(
            @RequestBody Set<Long> ids
    ) {
        return facultyService.searchByIds(ids);
    }
}
