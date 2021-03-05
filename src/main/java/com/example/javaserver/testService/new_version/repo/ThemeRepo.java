package com.example.javaserver.testService.new_version.repo;

import com.example.javaserver.common_data.model.Theme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ThemeRepo extends JpaRepository<Theme, Long> {
    List<Theme> findAllBySubjectId(Long subjectId);
}
