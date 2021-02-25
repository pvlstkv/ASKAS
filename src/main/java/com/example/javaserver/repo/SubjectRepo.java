package com.example.javaserver.repo;

import com.example.javaserver.model.common_data.Subject;
import org.springframework.data.repository.CrudRepository;

public interface SubjectRepo  extends CrudRepository<Subject, Long> {
}