package com.example.javaserver.study.controller.mapper;

import com.example.javaserver.study.model.UserFile;
import com.example.javaserver.study.service.UserFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
@Component
public class UserFileIdMapper {
    private final UserFileService service;

    @Autowired
    public UserFileIdMapper(UserFileService service) {
        this.service = service;
    }

    public UserFile getById(Long id) {
        return id == null
                ? null
                : service.getById(id);
    }

    public Long getId(UserFile userFile) {
        return userFile == null
                ? null
                : userFile.getId();
    }

    public Set<UserFile> getByIds(Set<Long> ids) {
        return ids == null
                ? null
                : service.getByIds(ids);
    }

    public Set<Long> getIds(Set<UserFile> userFiles) {
        return userFiles == null
                ? null
                : userFiles.stream().map(UserFile::getId).collect(Collectors.toSet());
    }
}
