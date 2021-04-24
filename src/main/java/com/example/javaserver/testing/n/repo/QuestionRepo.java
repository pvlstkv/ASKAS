package com.example.javaserver.testing.n.repo;

import com.example.javaserver.testing.n.model.QuestionData;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Set;

public interface QuestionRepo extends JpaRepository<QuestionData, Long> {
    @Transactional
    void deleteAllByIdIn(Set<Long> ids);

    Set<QuestionData> findAllByThemeId(Long themeId);
}
