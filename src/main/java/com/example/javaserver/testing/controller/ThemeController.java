package com.example.javaserver.testing.controller;

import com.example.javaserver.testing.model.Theme;
import com.example.javaserver.testing.model.dto.ThemeIn;
import com.example.javaserver.testing.model.dto.ThemeUpdateIn;
import com.example.javaserver.testing.service.ThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/testing/")
public class ThemeController {

    private final ThemeService themeService;

    @Autowired
    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    @GetMapping("/themes")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"USER", "TEACHER", "ADMIN"})
    public List<Theme> fetchThemesBySubjectId(
            @RequestParam(value = "subj_id") Long subjectId
    ) {
        return themeService.fetchSubjectThemes(subjectId);
    }

    @PostMapping("/theme")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"TEACHER", "ADMIN"})
    public Long createTheme(
            @RequestBody ThemeIn themeIn
    ) {
        return themeService.createTheme(themeIn);
    }

    @PutMapping("/theme")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"TEACHER", "ADMIN"})
    public void updateTheme(
            @RequestBody ThemeUpdateIn themeUpdateIn
    ) {
        themeService.updateTheme(themeUpdateIn);
    }

    @DeleteMapping("/themes")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"TEACHER", "ADMIN"})
    public void deleteThemes(
            @RequestBody List<Long> ids
    ) {
        themeService.deleteThemes(ids);
    }
}
