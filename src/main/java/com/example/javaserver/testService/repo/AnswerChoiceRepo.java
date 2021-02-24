package com.example.javaserver.testService.repo;//package com.example.testService.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AnswerChoiceRepo extends JpaRepository<AnswerChoiceRepo, Long> {

    @Query(value = "Select currval (pg_get_serial_sequence('answer_choice', 'id'))", nativeQuery = true)
    Optional<Long> getCurrValId();
}
