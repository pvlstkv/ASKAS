package com.example.javaserver.study.controller.dto;

import com.example.javaserver.study.model.LiteratureType;
import com.example.javaserver.study.model.TaskType;
import com.example.javaserver.study.model.UserFile;

import java.util.Set;

public class LiteratureIn {
    public Long id;
    private LiteratureType type;
    private String title;
    private String authors;
    private String description;
    private Set<UserFile> files;

    public LiteratureIn() { }
}
