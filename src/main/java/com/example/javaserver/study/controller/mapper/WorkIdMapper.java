package com.example.javaserver.study.controller.mapper;

import com.example.javaserver.study.model.Work;
import com.example.javaserver.study.repo.WorkRepo;
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
public class WorkIdMapper {
    private final WorkRepo repo;

    @Autowired
    public WorkIdMapper(WorkRepo repo) {
        this.repo = repo;
    }

    public Work getById(Long id) {
        Optional<Work> workO = repo.findByIdEquals(id);
        if (workO.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Работа с указанным id не существует");
        }
        return workO.get();
    }

    public Long getId(Work work) {
        return work == null
                ? null
                : work.getId();
    }

    public Set<Work> getByIds(Set<Long> ids) {
        Set<Work> works = repo.getWorksByIdIn(ids);
        if (works.size() == ids.size()) {
            return works;
        } else {
            Collection<Long> foundIds = works.stream()
                    .map(Work::getId)
                    .collect(Collectors.toSet());
            Collection<Long> notFoundIds = ids.stream()
                    .filter(i -> !foundIds.contains(i))
                    .collect(Collectors.toSet());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Работы с id: " + Arrays.toString(notFoundIds.toArray()) + " не существуют");
        }
    }

    public Set<Long> getIds(Set<Work> works) {
        return works == null
                ? null
                : works.stream().map(Work::getId).collect(Collectors.toSet());
    }
}
