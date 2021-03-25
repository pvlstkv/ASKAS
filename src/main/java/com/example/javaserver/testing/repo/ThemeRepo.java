package com.example.javaserver.testing.repo;

import com.example.javaserver.testing.model.Theme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ThemeRepo extends JpaRepository<Theme, Long> {
    List<Theme> findAllBySubjectId(Long subjectId);
}
