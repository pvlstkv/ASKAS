package com.example.javaserver.study.controller.dto;

import com.example.javaserver.study.model.LiteratureType;

import javax.validation.constraints.NotNull;
import java.util.Set;

public class LiteratureIn {
    @NotNull(message = "Необходимо указать тип")
    public LiteratureType type;
    public String title;
    public String authors;
    public String description;
    @NotNull
    public Set<Long> semesterIds;
    @NotNull
    public Set<Long> fileIds;

    public LiteratureIn() { }
}
