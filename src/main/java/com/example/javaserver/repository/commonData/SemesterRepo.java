package com.example.javaserver.repository.commonData;

import com.example.javaserver.model.commonData.Semester;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SemesterRepo extends JpaRepository<Semester, Long> {
}
