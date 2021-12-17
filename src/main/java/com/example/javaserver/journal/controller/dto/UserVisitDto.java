package com.example.javaserver.journal.controller.dto;

import com.example.javaserver.journal.model.Visit;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserVisitDto {
    private VisitDto visit;
    private Long lessonDate;

    public UserVisitDto() {
    }
}
