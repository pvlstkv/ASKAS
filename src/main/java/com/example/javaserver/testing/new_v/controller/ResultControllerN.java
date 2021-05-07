package com.example.javaserver.testing.new_v.controller;

import com.example.javaserver.general.model.UserDetailsImp;

import com.example.javaserver.testing.new_v.dto.answer.for_test.result.AfterCheckTestDto;
import com.example.javaserver.testing.new_v.model.saving_result.PassedTestN;
import com.example.javaserver.testing.new_v.dto.test.PassedTestPerUserDto;
import com.example.javaserver.testing.theme.dto.theme.PassedThemeDto;
import com.example.javaserver.testing.new_v.service.ResultServiceN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "testing/new/")
public class ResultControllerN {

    private final ResultServiceN resultServiceN;

    @Autowired
    public ResultControllerN(ResultServiceN resultServiceN) {
        this.resultServiceN = resultServiceN;
    }

    @GetMapping("/test/result/all")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    public List<AfterCheckTestDto> fetchAllPassedTests(
            @AuthenticationPrincipal UserDetailsImp userDetails
    ) {
        return resultServiceN.formUserPassedTest(userDetails);
    }

    @GetMapping("/test/result")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    public List<AfterCheckTestDto> fetchPassedTests(
            @RequestParam(value = "theme_id") Long themeId,
            @RequestParam(value = "user_id") Integer userId,
            @AuthenticationPrincipal UserDetailsImp userDetails
    ) {
        return resultServiceN.fetchUserPassedTestsByThemeAndUserId(userId, themeId, userDetails);
    }

    @GetMapping("/test/passed-themes")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    public List<PassedThemeDto> fetchPassedThemes(
            @RequestParam(value = "subj_id") Long subjectId,
            @RequestParam(value = "user_id") Integer userId,
            @AuthenticationPrincipal UserDetailsImp userDetails

    ) {
        return resultServiceN.fetchUserPassedThemesBySubjectIdAndUserId(userId, subjectId, userDetails);
    }

    @GetMapping("/test/passed-themes-by-group")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"TEACHER", "ADMIN"})
    public List<PassedTestPerUserDto> fetchPassedThemesByGroup(
            @RequestParam(value = "group_id") Long groupId,
            @RequestParam(value = "theme_id") Long themeId
    ) {
        return resultServiceN.fetchPassedThemesUsersByGroupId(groupId, themeId);
    }
}
