package com.example.javaserver.journal.controller.mapper;

import com.example.javaserver.journal.model.Visit;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
public class UserVisit {
    private Visit visit;
    private OffsetDateTime lessonDate;

    public UserVisit() {
    }
}
