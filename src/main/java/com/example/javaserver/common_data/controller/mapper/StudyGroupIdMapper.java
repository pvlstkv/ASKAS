package com.example.javaserver.common_data.controller.mapper;

import com.example.javaserver.common_data.model.StudyGroup;
import com.example.javaserver.common_data.repo.StudyGroupRepo;
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
public class StudyGroupIdMapper {
    private final StudyGroupRepo repo;

    @Autowired
    public StudyGroupIdMapper(StudyGroupRepo repo) {
        this.repo = repo;
    }

    public StudyGroup getById(Long id) {
        Optional<StudyGroup> studyGroup = repo.findByIdEquals(id);
        if (studyGroup.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Семестр с указанным id не существует");
        }
        return studyGroup.get();
    }

    public Long getId(StudyGroup group) {
        return group == null
                ? null
                : group.getId();
    }

    public Set<StudyGroup> getByIds(Set<Long> ids) {
        Set<StudyGroup> groups = repo.findAllByIdIn(ids);
        if (groups.size() == ids.size()) {
            return groups;
        } else {
            Collection<Long> foundIds = groups.stream()
                    .map(StudyGroup::getId)
                    .collect(Collectors.toSet());
            Collection<Long> notFoundIds = ids.stream()
                    .filter(i -> !foundIds.contains(i))
                    .collect(Collectors.toSet());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Группы с id: " + Arrays.toString(notFoundIds.toArray()) + " не существуют");
        }
    }

    public Set<Long> getIds(Set<StudyGroup> groups) {
        return groups == null
                ? null
                : groups.stream().map(StudyGroup::getId).collect(Collectors.toSet());
    }
}
