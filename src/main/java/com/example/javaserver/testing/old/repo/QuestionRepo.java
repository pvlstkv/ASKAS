package com.example.javaserver.testing.old.repo;


import com.example.javaserver.testing.old.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepo extends JpaRepository<Question, Long> {
    List<Question> findAllByThemeId(Long themeId);

//    void deleteAll(List<Long> ids);
}
