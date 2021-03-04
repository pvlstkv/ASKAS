package com.example.javaserver.testService.new_version.repo;



import com.example.javaserver.common_data.model.Theme;
import com.example.javaserver.testService.new_version.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface QuestionRepo extends JpaRepository<Question, Long> {
    List<Question> findAllByTheme(Theme theme);
}
