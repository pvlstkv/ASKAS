package com.example.javaserver.repo;

import com.example.javaserver.model.common_data.Studying;
import org.springframework.data.repository.CrudRepository;

public interface SemesterRepo  extends CrudRepository<Studying, Long> {
}
