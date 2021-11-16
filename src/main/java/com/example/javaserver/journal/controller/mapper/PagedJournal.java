package com.example.javaserver.journal.controller.mapper;

import com.example.javaserver.journal.model.Journal;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PagedJournal {
    private List<Journal> journals;
    private Long currentPage;
    private Long totalPage;
}
