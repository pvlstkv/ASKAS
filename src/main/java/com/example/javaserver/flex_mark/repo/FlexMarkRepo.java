package com.example.javaserver.flex_mark.repo;

import com.example.javaserver.flex_mark.model.FlexMark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FlexMarkRepo extends JpaRepository<FlexMark, Long> {

    Optional<FlexMark> findByTeacherIdAndStudyGroupIdAndSubjectSemesterId(Integer teacherId, Long studyGroupId, Long subjectSemesterId);

    Optional<FlexMark> findBySubjectSemesterIdAndStudyGroupId(Long subjectSemesterId, Long studyGroupId);
}
