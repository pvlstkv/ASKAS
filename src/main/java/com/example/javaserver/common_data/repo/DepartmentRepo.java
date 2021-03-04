package com.example.javaserver.common_data.repo;

import com.example.javaserver.common_data.model.Department;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface DepartmentRepo extends CrudRepository<Department, Long> {
    Optional<Department> findByShortName(String name);
}
