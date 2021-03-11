package com.example.javaserver.testing.repo;



import com.example.javaserver.common_data.model.Theme;
import com.example.javaserver.testing.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface QuestionRepo extends JpaRepository<Question, Long> {
    List<Question> findAllByTheme(Theme theme);
}
