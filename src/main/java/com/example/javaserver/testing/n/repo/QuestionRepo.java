package com.example.javaserver.testing.n.repo;

import com.example.javaserver.testing.n.model.QuestionData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepo extends JpaRepository<QuestionData, Long> {
}
