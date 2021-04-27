package com.example.javaserver.testing.new_v.repo.question;

import com.example.javaserver.testing.new_v.model.question.SelectableQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SelectableQuestionRepo extends JpaRepository<SelectableQuestion, Long> {

}
