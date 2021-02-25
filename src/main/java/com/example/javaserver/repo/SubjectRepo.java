package com.example.javaserver.repo;

import com.example.javaserver.model.common_data.Subject;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SubjectRepo  extends CrudRepository<Subject, Long> {
    Optional<Subject> findByName(String name);
    boolean existsByName(String name);

}