package com.example.javaserver.common_data.repo;

import com.example.javaserver.common_data.model.Faculty;
import org.springframework.data.repository.CrudRepository;

public interface FacultyRepo extends CrudRepository<Faculty, Long> {
}