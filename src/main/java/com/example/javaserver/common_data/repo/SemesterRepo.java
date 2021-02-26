package com.example.javaserver.common_data.repo;

import com.example.javaserver.common_data.model.Studying;
import org.springframework.data.repository.CrudRepository;

public interface SemesterRepo  extends CrudRepository<Studying, Long> {
}
