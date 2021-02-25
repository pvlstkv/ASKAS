package com.example.javaserver.testService.repo;



import com.example.javaserver.testService.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface QuestionRepo extends JpaRepository<Question, Integer> {
    List<Question> findAllBySubjectId(Integer id);
}
