package com.example.javaserver.common_data.controller.mapper;

import com.example.javaserver.common_data.model.StudyGroup;
import com.example.javaserver.common_data.model.Subject;
import com.example.javaserver.common_data.model.SubjectSemester;
import com.example.javaserver.common_data.repo.StudyGroupRepo;
import com.example.javaserver.common_data.repo.SubjectRepo;
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
public class SubjectIdMapper {
    private final SubjectRepo repo;

    @Autowired
    public SubjectIdMapper(SubjectRepo repo) {
        this.repo = repo;
    }

    public Subject getById(Long id) {
        Optional<Subject> subject = repo.findByIdEquals(id);
        if (subject.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Subject с указанным id не существует");
        }
        return subject.get();
    }

    public Long getId(Subject subject) {
        return subject == null
                ? null
                : subject.getId();
    }

    public Set<Subject> getByIds(Set<Long> ids) {
        Set<Subject> subjects = repo.findAllByIdIn(ids);
        if (subjects.size() == ids.size()) {
            return subjects;
        } else {
            Collection<Long> foundIds = subjects.stream()
                    .map(Subject::getId)
                    .collect(Collectors.toSet());
            Collection<Long> notFoundIds = ids.stream()
                    .filter(i -> !foundIds.contains(i))
                    .collect(Collectors.toSet());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Subject с id: " + Arrays.toString(notFoundIds.toArray()) + " не существуют");
        }
    }

    public Set<Long> getIds(Set<Subject> subjects) {
        return subjects == null
                ? null
                : subjects.stream().map(Subject::getId).collect(Collectors.toSet());
    }
}
