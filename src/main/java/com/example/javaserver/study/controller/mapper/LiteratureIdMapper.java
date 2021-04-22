package com.example.javaserver.study.controller.mapper;

import com.example.javaserver.study.model.Literature;
import com.example.javaserver.study.service.LiteratureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
@Component
public class LiteratureIdMapper {
    private final LiteratureService service;

    @Autowired
    public LiteratureIdMapper(LiteratureService service) {
        this.service = service;
    }

    public Literature getById(Long id) {
        return id == null
                ? null
                : service.getById(id);
    }

    public Long getId(Literature literature) {
        return literature == null
                ? null
                : literature.getId();
    }

    public Set<Literature> getByIds(Set<Long> ids) {
        return ids == null
                ? null
                : service.getByIds(ids);
    }

    public Set<Long> getIds(Set<Literature> literature) {
        return literature == null
                ? null
                : literature.stream().map(Literature::getId).collect(Collectors.toSet());
    }
}
