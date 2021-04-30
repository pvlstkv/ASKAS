package com.example.javaserver.testing.new_v.repo.question;

import com.example.javaserver.testing.new_v.model.question.QuestionData;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

public interface QuestionDataRepo extends JpaRepository<QuestionData, Long> {
    @Transactional
    void deleteAllByIdIn(Set<Long> ids);

    List<QuestionData> findAllByThemeId(Long themeId);
}
