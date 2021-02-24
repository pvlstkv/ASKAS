package com.example.javaserver.testService.repo;

import com.example.javaserver.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubjectRepo extends JpaRepository<Subject,Long> {
     Optional<Subject> findByName(String name);
}
