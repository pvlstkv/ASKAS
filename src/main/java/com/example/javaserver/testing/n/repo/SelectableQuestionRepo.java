package com.example.javaserver.testing.n.repo;

import com.example.javaserver.testing.n.model.SelectableQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SelectableQuestionRepo extends JpaRepository<SelectableQuestion, Long> {

}
