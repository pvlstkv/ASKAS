package com.example.javaserver.common_data.controller.mapper;

import com.example.javaserver.common_data.model.Subject;
import com.example.javaserver.common_data.model.SubjectSemester;
import com.example.javaserver.common_data.repo.SubjectRepo;
import com.example.javaserver.common_data.repo.SubjectSemesterRepo;
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
public class SubjectSemesterIdMapper {
    private final SubjectSemesterRepo repo;

    @Autowired
    public SubjectSemesterIdMapper(SubjectSemesterRepo repo) {
        this.repo = repo;
    }

    public SubjectSemester getById(Long id) {
        Optional<SubjectSemester> subject = repo.findByIdEquals(id);
        if (subject.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "SubjectSemester с указанным id не существует");
        }
        return subject.get();
    }

    public Long getId(SubjectSemester subject) {
        return subject == null
                ? null
                : subject.getId();
    }

    public Set<SubjectSemester> getByIds(Set<Long> ids) {
        Set<SubjectSemester> subjects = repo.findAllByIdIn(ids);
        if (subjects.size() == ids.size()) {
            return subjects;
        } else {
            Collection<Long> foundIds = subjects.stream()
                    .map(SubjectSemester::getId)
                    .collect(Collectors.toSet());
            Collection<Long> notFoundIds = ids.stream()
                    .filter(i -> !foundIds.contains(i))
                    .collect(Collectors.toSet());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "SubjectSemester с id: " + Arrays.toString(notFoundIds.toArray()) + " не существуют");
        }
    }

    public Set<Long> getIds(Set<SubjectSemester> subjects) {
        return subjects == null
                ? null
                : subjects.stream().map(SubjectSemester::getId).collect(Collectors.toSet());
    }
}
