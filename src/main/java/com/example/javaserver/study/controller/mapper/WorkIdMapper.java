package com.example.javaserver.study.controller.mapper;

import com.example.javaserver.study.model.Work;
import com.example.javaserver.study.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
@Component
public class WorkIdMapper {
    private final WorkService service;

    @Autowired
    public WorkIdMapper(WorkService service) {
        this.service = service;
    }

    public Work getById(Long id) {
        return id == null
                ? null
                : service.getById(id);
    }

    public Long getId(Work work) {
        return work == null
                ? null
                : work.getId();
    }

    public Set<Work> getByIds(Set<Long> ids) {
        return ids == null
                ? null
                : service.getByIds(ids);
    }

    public Set<Long> getIds(Set<Work> works) {
        return works == null
                ? null
                : works.stream().map(Work::getId).collect(Collectors.toSet());
    }
}
