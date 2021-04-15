package com.example.javaserver.study.controller.dto;

import com.example.javaserver.study.model.LiteratureType;
import com.example.javaserver.study.model.TaskType;
import com.example.javaserver.study.model.UserFile;

import javax.validation.constraints.NotNull;
import java.util.Set;

public class LiteratureIn {
    @NotNull
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
