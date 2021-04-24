package com.example.javaserver.testing.n.repo;

import com.example.javaserver.testing.n.model.MatchableQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchableQuestionRepo extends JpaRepository<MatchableQuestion, Long> {
}
