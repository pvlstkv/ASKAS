package com.example.javaserver.study.controller;

import com.example.javaserver.general.criteria.SearchCriteria;
import com.example.javaserver.general.model.Message;
import com.example.javaserver.general.model.UserDetailsImp;
import com.example.javaserver.study.controller.dto.LiteratureIn;
import com.example.javaserver.study.model.Literature;
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

    @Autowired
    public LiteratureController(LiteratureService literatureService) {
        this.literatureService = literatureService;
    }

    @ResponseStatus(HttpStatus.OK)
    @Secured({"TEACHER", "ADMIN"})
    @PostMapping
    public Message create(
            @RequestBody @Valid LiteratureIn literatureIn,
            @AuthenticationPrincipal UserDetailsImp userDetails
    ) {
        return literatureService.create(literatureIn, userDetails);
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
    @PatchMapping
    public Message update(
            @RequestParam("id") Long id,
            @RequestBody @Valid LiteratureIn literatureIn
    ) {
        return literatureService.update(id, literatureIn);
    }

    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    @GetMapping("/all")
    public Collection<Literature> getAll() {
        return literatureService.getAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    @PostMapping("/criteria-search")
    public Collection<Literature> criteriaSearch(
            @RequestBody Set<SearchCriteria> criteria
    ) {
        return literatureService.criteriaSearch(criteria);
    }

    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    @PostMapping("/search-by-ids")
    public Collection<Literature> searchByIds(
            @RequestBody Set<Long> ids
    ) {
        return literatureService.searchByIds(ids);
    }

    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    @GetMapping("/learning")
    public Collection<Literature> searchBySubjectAndStudent(
            @RequestParam("subjectId") Long subjectId,
            @RequestParam(value = "userId", required = false) Integer userId,
            @AuthenticationPrincipal UserDetailsImp userDetails
    ) {
        return literatureService.searchBySubjectAndStudent(subjectId, userId, userDetails);
    }

    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    @GetMapping
    public Collection<Literature> searchBySubject(
            @RequestParam("subjectId") Long subjectId
    ) {
        return literatureService.searchBySubject(subjectId);
    }
}
