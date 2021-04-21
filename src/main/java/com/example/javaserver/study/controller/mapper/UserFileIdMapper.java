package com.example.javaserver.study.controller.mapper;

import com.example.javaserver.study.model.UserFile;
import com.example.javaserver.study.repo.UserFileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
@Component
public class UserFileIdMapper {
    private final UserFileRepo repo;

    @Autowired
    public UserFileIdMapper(UserFileRepo repo) {
        this.repo = repo;
    }

    public UserFile getById(Long id) {
        Optional<UserFile> userFileO = repo.findByIdEquals(id);
        if (userFileO.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Файл с указанным id не существует");
        }
        return userFileO.get();
    }

    public Long getId(UserFile userFile) {
        return userFile == null
                ? null
                : userFile.getId();
    }

    public Set<UserFile> getByIds(Set<Long> ids) {
        Set<UserFile> userFiles = repo.findAllByIdIn(ids);
        if (userFiles.size() == ids.size()) {
            return userFiles;
        } else {
            Collection<Long> foundIds = userFiles.stream()
                    .map(UserFile::getId)
                    .collect(Collectors.toSet());
            Collection<Long> notFoundIds = ids.stream()
                    .filter(i -> !foundIds.contains(i))
                    .collect(Collectors.toSet());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Файлы с id: " + Arrays.toString(notFoundIds.toArray()) + " не существуют");
        }
    }

    public Set<Long> getIds(Set<UserFile> userFiles) {
        return userFiles == null
                ? null
                : userFiles.stream().map(UserFile::getId).collect(Collectors.toSet());
    }
}
