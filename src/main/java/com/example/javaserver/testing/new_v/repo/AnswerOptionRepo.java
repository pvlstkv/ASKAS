package com.example.javaserver.testing.new_v.repo;

import com.example.javaserver.testing.new_v.model.answer.AnswerOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerOptionRepo extends JpaRepository<AnswerOption, Long> {
}
