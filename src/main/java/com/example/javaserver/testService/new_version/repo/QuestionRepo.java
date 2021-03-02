package com.example.javaserver.testService.new_version.repo;



import com.example.javaserver.testService.new_version.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface QuestionRepo extends JpaRepository<Question, Integer> {
    List<Question> findAllByIdAndByThemeId(Long id, Long themeId);
}
