package com.example.javaserver.common_data.controller;

import com.example.javaserver.common_data.model.StudyGroup;
import com.example.javaserver.common_data.service.StudyGroupService;
import com.example.javaserver.general.service.RequestHandlerService;
import com.example.javaserver.user.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.EnumSet;
import java.util.Set;

@RestController
@RequestMapping("/study-group")
public class StudyGroupController {
    private final RequestHandlerService requestHandlerService;
    private final StudyGroupService studyGroupService;

    @Autowired
    public StudyGroupController(RequestHandlerService requestHandlerService, StudyGroupService studyGroupService) {
        this.requestHandlerService = requestHandlerService;
        this.studyGroupService = studyGroupService;
    }

    @PostMapping("/creation")
    public ResponseEntity<?> create(
            @RequestHeader("token") String token,
            @RequestBody StudyGroup studyGroup
    ) {
        return requestHandlerService.proceed(
                token,
                (c) -> studyGroupService.create(studyGroup),
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
}
