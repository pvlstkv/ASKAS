package com.example.javaserver.study.controller;

import com.example.javaserver.general.criteria.SearchCriteria;
import com.example.javaserver.general.model.Message;
import com.example.javaserver.general.model.UserDetailsImp;
import com.example.javaserver.general.service.RequestHandlerService;
import com.example.javaserver.study.controller.dto.TaskIn;
import com.example.javaserver.study.controller.dto.WorkIn;
import com.example.javaserver.study.model.Work;
import com.example.javaserver.study.service.WorkService;
import com.example.javaserver.user.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/work")
public class WorkController {
    private final RequestHandlerService requestHandlerService;
    private final WorkService workService;

    @Autowired
    public WorkController(RequestHandlerService requestHandlerService, WorkService workService) {
        this.requestHandlerService = requestHandlerService;
        this.workService = workService;
    }


    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    @PostMapping
    public Message create(
            @AuthenticationPrincipal UserDetailsImp userDetails,
            @RequestBody WorkIn workIn
    ) {
        return workService.create(workIn, userDetails);
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
    @PatchMapping
    public Message update(
            @RequestBody WorkIn workIn
    ) {
        return workService.update(workIn);
    }

    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    @GetMapping("/all")
    public Collection<Work> getAll() {
        return workService.getAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    @PostMapping("/criteria-search")
    public Collection<Work> criteriaSearch(
            @RequestBody Set<SearchCriteria> criteria
    ) {
        return workService.criteriaSearch(criteria);
    }

    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    @PostMapping("/search-by-ids")
    public Collection<Work> searchByIds(
            @RequestBody Set<Long> ids
    ) {
        return  workService.searchByIds(ids);
    }
}
