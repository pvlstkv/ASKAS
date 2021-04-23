package com.example.javaserver.common_data.controller;

import com.example.javaserver.common_data.controller.dto.SubjectIn;
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

    @Autowired
    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Secured({"ADMIN"})
    public Message create(
            @RequestBody SubjectIn subjectIn
    ) {
        return subjectService.create(subjectIn);
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
    public Collection<Subject> search() {
        return subjectService.getAll();
    }

    @PostMapping("/criteria-search")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    public Collection<Subject> criteriaSearch(
            @RequestBody Set<SearchCriteria> criteria
    ) {
        return subjectService.criteriaSearch(criteria);
    }

    @PostMapping("/search-by-ids")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    public Collection<Subject> searchByIds(
            @RequestBody Set<Long> ids
    ) {
        return subjectService.getByIds(ids);
    }

    @GetMapping("/learning")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    public Collection<Subject> searchByStudentId(
            @RequestParam(value = "userId", required = false) Integer userId,
            @AuthenticationPrincipal UserDetailsImp userDetails
    ) {
        return subjectService.searchByStudentId(userId, userDetails);
    }

    @GetMapping("/teaching")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    public Collection<Subject> searchByTeacherId(
            @RequestParam(value = "userId", required = false) Integer userId,
            @AuthenticationPrincipal UserDetailsImp userDetails
    ) {
        return subjectService.searchByTeacherId(userId, userDetails);
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
