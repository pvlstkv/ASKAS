package com.example.javaserver.study.controller.mapper;

import com.example.javaserver.study.model.Literature;
import com.example.javaserver.study.repo.LiteratureRepo;
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
public class LiteratureIdMapper {
    private final LiteratureRepo repo;

    @Autowired
    public LiteratureIdMapper(LiteratureRepo repo) {
        this.repo = repo;
    }

    public Literature getById(Long id) {
        Optional<Literature> literatureO = repo.findByIdEquals(id);
        if (literatureO.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Литература с указанным id не существует");
        }
        return literatureO.get();
    }

    public Long getId(Literature literature) {
        return literature == null
                ? null
                : literature.getId();
    }

    public Set<Literature> getByIds(Set<Long> ids) {
        Set<Literature> literature = repo.findAllByIdIn(ids);
        if (literature.size() == ids.size()) {
            return literature;
        } else {
            Collection<Long> foundIds = literature.stream()
                    .map(Literature::getId)
                    .collect(Collectors.toSet());
            Collection<Long> notFoundIds = ids.stream()
                    .filter(i -> !foundIds.contains(i))
                    .collect(Collectors.toSet());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Литература с id: " + Arrays.toString(notFoundIds.toArray()) + " не существуют");
        }
    }

    public Set<Long> getIds(Set<Literature> literature) {
        return literature == null
                ? null
                : literature.stream().map(Literature::getId).collect(Collectors.toSet());
    }
}
