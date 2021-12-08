package com.example.javaserver.journal.repo;

import com.example.javaserver.journal.model.Journal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface JournalRepo extends JpaRepository<Journal, Long> {
    Page<Journal> findBySubjectSemesterIdAndStudyGroupId(Long semesterId, Long groupId, Pageable pageable);

    Page<Journal> findAllByCreatedDateAfterAndCreatedDateBefore(OffsetDateTime timeFrom, OffsetDateTime timeTo,
                                                                Pageable pageable);

    Page<Journal> findAllBySubjectSemesterIdAndStudyGroupIdAndCreatedDateAfterAndCreatedDateBefore
            (Long semesterId, Long groupId, OffsetDateTime after, OffsetDateTime before, Pageable pageable);

    List<Journal> findAllBySubjectSemesterIdAndStudyGroupIdAndCreatedDateAfterAndCreatedDateBefore
            (Long semesterId, Long groupId, OffsetDateTime after, OffsetDateTime before);


}
