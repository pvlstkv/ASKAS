package com.example.javaserver.common_data.repo;

import com.example.javaserver.common_data.model.SubjectSemester;
import org.springframework.data.repository.CrudRepository;

public interface SubjectSemesterRepo extends CrudRepository<SubjectSemester, Long> {
}