package com.example.javaserver.common_data.repo;

import com.example.javaserver.common_data.model.StudyGroup;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface StudyGroupRepo  extends CrudRepository<StudyGroup, Long> {
    Optional<StudyGroup> findByShortName(String name);
    boolean existsByShortName(String name);
}