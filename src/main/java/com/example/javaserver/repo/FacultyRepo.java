package com.example.javaserver.repo;

import com.example.javaserver.model.common_data.Faculty;
import org.springframework.data.repository.CrudRepository;

public interface FacultyRepo extends CrudRepository<Faculty, Long> {
}