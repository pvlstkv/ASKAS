package com.example.javaserver.testService.new_version.repo;

import com.example.javaserver.testService.new_version.models.saving_results.PassedTest;
import com.example.javaserver.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PassedTestRepo extends JpaRepository<PassedTest, Long> {
    List<PassedTest> findAllByUser(User user);
}
