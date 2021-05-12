package com.example.javaserver.testing.new_v.repo.question;

import com.example.javaserver.testing.new_v.model.question.WriteableQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WriteableQuestionRepo extends JpaRepository<WriteableQuestion, Long> {

}
