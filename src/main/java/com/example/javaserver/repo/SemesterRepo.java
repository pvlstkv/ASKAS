package com.example.javaserver.repo;

import com.example.javaserver.model.common_data.Semester;
import org.springframework.data.repository.CrudRepository;

public interface SemesterRepo  extends CrudRepository<Semester, Long> {
}
