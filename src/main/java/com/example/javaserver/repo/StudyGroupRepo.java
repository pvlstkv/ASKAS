package com.example.javaserver.repo;

import com.example.javaserver.model.common_data.StudyGroup;
import org.springframework.data.repository.CrudRepository;

public interface StudyGroupRepo  extends CrudRepository<StudyGroup, Long> {
}