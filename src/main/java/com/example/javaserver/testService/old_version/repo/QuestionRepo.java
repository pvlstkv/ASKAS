package com.example.javaserver.testService.old_version.repo;



import com.example.javaserver.testService.old_version.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface QuestionRepo extends JpaRepository<Question, Integer> {
    List<Question> findAllBySubjectId(Integer id);
}
