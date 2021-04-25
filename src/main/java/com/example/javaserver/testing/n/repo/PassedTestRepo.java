package com.example.javaserver.testing.n.repo;

import com.example.javaserver.testing.n.model.saving_result.PassedTest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassedTestRepo extends JpaRepository<PassedTest, Long> {
}
