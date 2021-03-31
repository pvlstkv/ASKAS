package com.example.javaserver.common_data.controller;

import com.example.javaserver.common_data.controller.client_model.FacultyIn;
import com.example.javaserver.common_data.model.Faculty;
import com.example.javaserver.common_data.service.FacultyService;
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
@RequestMapping("/faculty")
public class FacultyController {
    private final RequestHandlerService requestHandlerService;
    private final FacultyService facultyService;

    @Autowired
    public FacultyController(RequestHandlerService requestHandlerService, FacultyService facultyService) {
        this.requestHandlerService = requestHandlerService;
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
    public ResponseEntity<?> delete(
            @RequestHeader("token") String token,
            @RequestBody Set<Long> ids
    ) {
        return requestHandlerService.proceed(
                token,
                (c) -> facultyService.delete(ids),
                EnumSet.of(UserRole.ADMIN)
        );
    }

    @PatchMapping
    public ResponseEntity<?> update(
            @RequestHeader("token") String token,
            @RequestParam("id") Long id,
            @RequestParam(value = "shortName", required = false) String shortName,
            @RequestParam(value = "fullName", required = false) String fullName
    ) {
        return requestHandlerService.proceed(
                token,
                (c) -> facultyService.update(id, shortName, fullName),
                EnumSet.of(UserRole.ADMIN)
        );
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    public Collection<Faculty> getAll() {
        return facultyService.getAll();
    }

    @PostMapping("/criteria-search")
    public ResponseEntity<?> criteriaSearch(
            @RequestHeader("token") String token,
            @RequestBody Set<SearchCriteria> criteria
    ) {
        return requestHandlerService.proceed(
                token,
                (c) -> facultyService.criteriaSearch(criteria),
                EnumSet.allOf(UserRole.class)
        );
    }

    @PostMapping("/search-by-ids")
    public ResponseEntity<?> searchByIds(
            @RequestHeader("token") String token,
            @RequestBody Set<Long> ids
    ) {
        return requestHandlerService.proceed(
                token,
                (c) -> facultyService.searchByIds(ids),
                EnumSet.allOf(UserRole.class)
        );
    }
}
