package com.example.javaserver.testing.new_v.repo.question;

import com.example.javaserver.testing.new_v.model.question.MatchableQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchableQuestionRepo extends JpaRepository<MatchableQuestion, Long> {
}
