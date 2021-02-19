package com.example.javaserver.repository.commonData;

import com.example.javaserver.model.commonData.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectRepo extends JpaRepository<Subject, Long> {
    List<Subject> findAllByNumberOfSemester(int semesterNumber);
}
