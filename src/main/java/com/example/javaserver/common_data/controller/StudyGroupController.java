package com.example.javaserver.common_data.controller;

import com.example.javaserver.common_data.controller.client_model.StudyGroupI;
import com.example.javaserver.common_data.model.StudyGroup;
import com.example.javaserver.common_data.repo.StudyGroupRepo;
import com.example.javaserver.common_data.service.StudyGroupService;
import com.example.javaserver.general.service.RequestHandlerService;
import com.example.javaserver.user.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/study-group")
public class StudyGroupController {
    private final RequestHandlerService requestHandlerService;
    private final StudyGroupService studyGroupService;
    private final StudyGroupRepo studyGroupRepo;

    @Autowired
    public StudyGroupController(RequestHandlerService requestHandlerService, StudyGroupService studyGroupService, StudyGroupRepo studyGroupRepo) {
        this.requestHandlerService = requestHandlerService;
        this.studyGroupService = studyGroupService;
        this.studyGroupRepo = studyGroupRepo;
    }

    @GetMapping
    public ResponseEntity<?> getGroup(
            @RequestHeader("token") String token,
            @RequestParam(required = false) Long id
    ){
        return requestHandlerService.proceed(token,userContext -> studyGroupService.get(id),EnumSet.allOf(UserRole.class));
    }

    @GetMapping("/list")
    public ResponseEntity<?> getListGroupStudy(){
        List<StudyGroup> studyGroups = (List<StudyGroup>) studyGroupRepo.findAll();
        return new ResponseEntity<>(studyGroups, HttpStatus.OK);
    }



    @PostMapping
    public ResponseEntity<?> create(
            @RequestHeader("token") String token,
            @RequestBody StudyGroupI studyGroupI
    ) {
        return requestHandlerService.proceed(
                token,
                (c) -> studyGroupService.create(studyGroupI),
                EnumSet.of(UserRole.ADMIN)
        );
    }

    @PostMapping("/enrollment")
    public ResponseEntity<?> create(
            @RequestHeader("token") String token,
            @RequestParam("study_group_id") Long studyGroupId,
            @RequestBody Set<Integer> userIds
    ) {
        return requestHandlerService.proceed(
                token,
                (c) -> studyGroupService.enroll(studyGroupId, userIds),
                EnumSet.of(UserRole.ADMIN)
        );
    }

    @PatchMapping("/subject_semester_addition")
    public ResponseEntity<?> addSubjectSemesters(
            @RequestHeader("token") String token,
            @RequestParam("id") Long studyGroupId,
            @RequestBody Set<Long> subjectSemesterIds
    ) {
        return requestHandlerService.proceed(
                token,
                (c) -> studyGroupService.addSubjectSemesters(studyGroupId, subjectSemesterIds),
                EnumSet.of(UserRole.ADMIN)
        );
    }

    @GetMapping("/teaching")
    public ResponseEntity<?> getGroupsByUser(
            @RequestHeader("token") String token,
            @RequestParam(value = "userId", required = false) Integer userId
    ){
        return requestHandlerService.proceed(
                token,
                (c) -> studyGroupService.getGroupsByUser(userId, c),
                EnumSet.allOf(UserRole.class)
        );
    }
}
