//package com.example.javaserver.testing.controller;
//
//import com.example.javaserver.general.model.UserDetailsImp;
//import com.example.javaserver.testing.model.Theme;
//import com.example.javaserver.testing.model.dto.PassedTestOut;
//import com.example.javaserver.testing.model.dto.PassedThemeOut;
//import com.example.javaserver.testing.model.saving_result.PassedTest;
//import com.example.javaserver.testing.service.ResultService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.access.annotation.Secured;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping(value = "api/testing/")
//public class ResultController {
//
//    private final ResultService resultService;
//
//    @Autowired
//    public ResultController(ResultService resultService) {
//        this.resultService = resultService;
//    }
//
//    @GetMapping("/test/result/all")
//    @ResponseStatus(HttpStatus.OK)
//    @Secured({"USER", "TEACHER", "ADMIN"})
//    public List<PassedTest> fetchAllPassedTests(
//            @AuthenticationPrincipal UserDetailsImp userDetails
//    ) {
//        return resultService.formUserPassedTest(userDetails);
//    }
//
//    @GetMapping("/test/result")
//    @ResponseStatus(HttpStatus.OK)
//    @Secured({"USER", "TEACHER", "ADMIN"})
//    public List<PassedTest> fetchPassedTests(
//            @RequestParam(value = "theme_id") Long themeId,
//            @RequestParam(value = "user_id") Integer userId,
//            @AuthenticationPrincipal UserDetailsImp userDetails
//    ) {
//        return resultService.fetchUserPassedTestsByThemeAndUserId(userId, themeId, userDetails);
//    }
//
//    @GetMapping("/test/passed-themes")
//    @ResponseStatus(HttpStatus.OK)
//    @Secured({"USER", "TEACHER", "ADMIN"})
//    public List<PassedThemeOut>  fetchPassedThemes(
//            @RequestParam(value = "subj_id") Long subjectId,
//            @RequestParam(value = "user_id") Integer userId,
//            @AuthenticationPrincipal UserDetailsImp userDetails
//
//    ) {
//        return resultService.fetchUserPassedThemesBySubjectIdAndUserId(userId, subjectId, userDetails);
//    }
//
//    @GetMapping("/test/passed-themes-by-group")
//    @ResponseStatus(HttpStatus.OK)
//    @Secured({"TEACHER", "ADMIN"})
//    public List<PassedTestOut> fetchPassedThemesByGroup(
//            @RequestParam(value = "group_id") Long groupId,
//            @RequestParam(value = "theme_id") Long themeId
//    ) {
//        return resultService.fetchPassedThemesUsersByGroupId(groupId, themeId);
//    }
//}
