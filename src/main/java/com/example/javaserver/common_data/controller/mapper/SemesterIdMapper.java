package com.example.javaserver.common_data.controller.mapper;

import com.example.javaserver.common_data.model.SubjectSemester;
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
public class SemesterIdMapper {
    private final SubjectSemesterRepo repo;

    @Autowired
    public SemesterIdMapper(SubjectSemesterRepo repo) {
        this.repo = repo;
    }

    public SubjectSemester getById(Long id) {
        Optional<SubjectSemester> semesterO = repo.findByIdEquals(id);
        if (semesterO.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Суместр с указанным id не существует");
        }
        return semesterO.get();
    }

    public Long getId(SubjectSemester semester) {
        return semester == null
                ? null
                : semester.getId();
    }

    public Set<SubjectSemester> getByIds(Set<Long> ids) {
        Set<SubjectSemester> semesters = repo.findAllByIdIn(ids);
        if (semesters.size() == ids.size()) {
            return semesters;
        } else {
            Collection<Long> foundIds = semesters.stream()
                    .map(SubjectSemester::getId)
                    .collect(Collectors.toSet());
            Collection<Long> notFoundIds = ids.stream()
                    .filter(i -> !foundIds.contains(i))
                    .collect(Collectors.toSet());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Семестры с id: " + Arrays.toString(notFoundIds.toArray()) + " не существуют");
        }
    }

    public Set<Long> getIds(Set<SubjectSemester> semesters) {
        return semesters == null
                ? null
                : semesters.stream().map(SubjectSemester::getId).collect(Collectors.toSet());
    }
}
