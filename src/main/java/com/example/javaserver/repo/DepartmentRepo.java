package com.example.javaserver.repo;

import com.example.javaserver.model.common_data.Department;
import org.springframework.data.repository.CrudRepository;

public interface DepartmentRepo extends CrudRepository<Department, Long> {
}
