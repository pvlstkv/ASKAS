package com.example.javaserver.common_data.controller;

import com.example.javaserver.common_data.controller.client_model.SubjectSemesterIn;
import com.example.javaserver.common_data.model.SubjectSemester;
import com.example.javaserver.common_data.service.SubjectSemesterService;
import com.example.javaserver.general.criteria.SearchCriteria;
import com.example.javaserver.general.model.Message;
import com.example.javaserver.general.service.RequestHandlerService;
import com.example.javaserver.user.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;

@RestController
@RequestMapping("/subject-semester")
public class SubjectSemesterController {
    private final RequestHandlerService requestHandlerService;
    private final SubjectSemesterService subjectSemesterService;

    @Autowired
    public SubjectSemesterController(RequestHandlerService requestHandlerService, SubjectSemesterService subjectSemesterService) {
        this.requestHandlerService = requestHandlerService;
        this.subjectSemesterService = subjectSemesterService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Secured({"ADMIN"})
    public Message create(
            @RequestBody SubjectSemesterIn subjectSemesterIn
    ) {
        return subjectSemesterService.create(subjectSemesterIn);
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
            @RequestParam(value = "numberOfSemester", required = false) String numberOfSemester,
            @RequestParam(value = "subjectId", required = false) String subjectId
    ) {
        return subjectSemesterService.update(id, controlType, hasCourseProject, hasCourseWork, numberOfSemester, subjectId);
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
    public Collection<SubjectSemester> search() {
        return subjectSemesterService.getAll();
    }

    @PostMapping("/criteria-search")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    public Collection<SubjectSemester> criteriaSearch(
            @RequestBody Set<SearchCriteria> criteria
    ) {
        return subjectSemesterService.criteriaSearch(criteria);
    }

    @PostMapping("/search-by-ids")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    public Collection<SubjectSemester> searchByIds(
            @RequestBody Set<Long> ids
    ) {
        return subjectSemesterService.searchByIds(ids);
    }
}
