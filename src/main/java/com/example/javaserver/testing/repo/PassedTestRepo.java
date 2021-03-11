package com.example.javaserver.testing.repo;

import com.example.javaserver.testing.models.saving_results.PassedTest;
import com.example.javaserver.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PassedTestRepo extends JpaRepository<PassedTest, Long> {
    List<PassedTest> findAllByUser(User user);
}
