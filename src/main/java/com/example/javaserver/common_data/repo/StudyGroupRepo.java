package com.example.javaserver.common_data.repo;

import com.example.javaserver.common_data.model.StudyGroup;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface StudyGroupRepo  extends CrudRepository<StudyGroup, Long> {
    Optional<StudyGroup> findByShortName(String name);
    Set<StudyGroup> findAllByIdIn(Set<Long> ids);
    boolean existsByShortName(String name);
}