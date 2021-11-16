package com.example.javaserver.journal.controller;

import com.example.javaserver.general.model.UserDetailsImp;
import com.example.javaserver.journal.controller.dto.JournalDto;
import com.example.javaserver.journal.controller.dto.PagedJournalDto;
import com.example.javaserver.journal.controller.mapper.JournalMapper;
import com.example.javaserver.journal.controller.mapper.PagedJournalMapper;
import com.example.javaserver.journal.service.JournalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(value = "the journal controller: for creating, getting and updating journals")
@RestController
@RequestMapping("/journal")
public class JournalController {
    private final JournalMapper journalMapper;
    private final JournalService journalService;
    private final PagedJournalMapper pagedJournalMapper;

    @Autowired
    public JournalController(JournalMapper journalMapper, JournalService journalService, PagedJournalMapper pagedJournalMapper) {
        this.journalMapper = journalMapper;
        this.journalService = journalService;
        this.pagedJournalMapper = pagedJournalMapper;
    }

    @ApiOperation(value = "create a one journal")
    @ResponseStatus(HttpStatus.CREATED)
    @Secured({"TEACHER", "ADMIN"})
    @PostMapping
    public void create(
            @RequestBody @Valid JournalDto journalDto,
            @AuthenticationPrincipal UserDetailsImp userDetails
    ) {
        journalService.saveJournal(
                journalMapper.toEntity(journalDto), userDetails
        );
    }

    @ApiOperation(value = "update a one journal by its id")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"TEACHER", "ADMIN"})
    @PutMapping
    public void update(
            @RequestBody @Valid JournalDto journalDto,
            @AuthenticationPrincipal UserDetailsImp userDetails
    ) {
        journalService.updateJournal(
                journalMapper.toEntity(journalDto), userDetails
        );
    }

    @ApiOperation(value = "get journals by semesterId and groupId", response = List.class)
    @ResponseStatus(HttpStatus.OK)
    @Secured({"TEACHER", "ADMIN"})
    @GetMapping
    public PagedJournalDto getBySemesterIdAndGroupId(
            @RequestParam("semesterId") Long semesterId,
            @RequestParam("groupId") Long groupId,
            @RequestParam(required = false) Long timeAfter,
            @RequestParam(required = false) Long timeBefore,
            @RequestParam(required = false, defaultValue = "0") Long pageNumber,
            @RequestParam(required = false, defaultValue = "5") Long pageSize

    ) {
        return pagedJournalMapper.toPagedJournalDto(
                journalService.getBySemesterIdAndGroupId(semesterId, groupId, timeAfter, timeBefore,
                        pageNumber, pageSize)
        );
    }
}
