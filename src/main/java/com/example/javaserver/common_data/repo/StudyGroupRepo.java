package com.example.javaserver.common_data.repo;

import com.example.javaserver.common_data.model.StudyGroup;
import org.springframework.data.repository.CrudRepository;

public interface StudyGroupRepo  extends CrudRepository<StudyGroup, Long> {
}