package com.example.javaserver.common_data.controller;

import com.example.javaserver.common_data.controller.client_model.StudyGroupI;
import com.example.javaserver.common_data.model.StudyGroup;
import com.example.javaserver.common_data.repo.StudyGroupRepo;
import com.example.javaserver.common_data.service.StudyGroupService;
import com.example.javaserver.general.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/study-group")
public class StudyGroupController {
    private final StudyGroupService studyGroupService;
    private final StudyGroupRepo studyGroupRepo;

    @Autowired
    public StudyGroupController(StudyGroupService studyGroupService, StudyGroupRepo studyGroupRepo) {
        this.studyGroupService = studyGroupService;
        this.studyGroupRepo = studyGroupRepo;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    public StudyGroup getGroup(
            @RequestParam(required = false) Long id
    ){
        return studyGroupService.get(id);
    }

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public Collection<StudyGroup> getListGroupStudy(){
        return (List<StudyGroup>) studyGroupRepo.findAll();
    }

    @GetMapping("/search-by-ids")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    public Collection<StudyGroup> searchByIds(
            @RequestParam("id") Set<Long> ids
    ) {
        return studyGroupService.searchByIds(ids);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Secured({"ADMIN"})
    public Message create(
            @RequestBody StudyGroupI studyGroupI
    ) {
        return studyGroupService.create(studyGroupI);
    }

    @PostMapping("/enrollment")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ADMIN"})
    public Message create(
            @RequestParam("study_group_id") Long studyGroupId,
            @RequestBody Set<Integer> userIds
    ) {
        return studyGroupService.enroll(studyGroupId, userIds);
    }

    @PatchMapping("/subject_semester_addition")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ADMIN"})
    public Message addSubjectSemesters(
            @RequestParam("id") Long studyGroupId,
            @RequestBody Set<Long> subjectSemesterIds
    ) {
        return studyGroupService.addSubjectSemesters(studyGroupId, subjectSemesterIds);
    }

    @GetMapping("/teaching")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    public Collection<StudyGroup> getGroupsByUser(
            @RequestParam(value = "userId", required = false) Integer userId
    ){
        return studyGroupService.getGroupsByUser(userId);
    }
}
