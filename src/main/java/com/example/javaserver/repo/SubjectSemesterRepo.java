package com.example.javaserver.repo;

import com.example.javaserver.model.common_data.SubjectSemester;
import org.springframework.data.repository.CrudRepository;

public interface SubjectSemesterRepo extends CrudRepository<SubjectSemester, Long> {
}