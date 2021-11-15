package com.example.javaserver.journal.repo;

import com.example.javaserver.journal.model.Journal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface JournalRepo extends JpaRepository<Journal, Long> {
    List<Journal> findBySubjectSemesterIdAndStudyGroupId(Long semesterId, Long groupId);
}
