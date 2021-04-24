package com.example.javaserver.testing.n.repo;

import com.example.javaserver.testing.n.model.WriteableQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WriteableQuestionRepo extends JpaRepository<WriteableQuestion, Long> {

}
