package com.example.javaserver.common_data.repo;

import com.example.javaserver.common_data.model.Mark;
import org.springframework.data.repository.CrudRepository;

public interface MarkRepo extends CrudRepository<Mark, Long> {
}
