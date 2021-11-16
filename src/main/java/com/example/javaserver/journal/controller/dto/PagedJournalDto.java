package com.example.javaserver.journal.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
public class PagedJournalDto {
    private final List<JournalDto> journals;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final Long currentPage;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final Long totalPage;

    public PagedJournalDto(List<JournalDto> journals, Long currentPage, Long totalPage) {
        this.journals = journals;
        this.currentPage = currentPage;
        this.totalPage = totalPage;
    }
}
