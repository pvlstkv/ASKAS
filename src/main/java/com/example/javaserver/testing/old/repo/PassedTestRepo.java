package com.example.javaserver.testing.old.repo;


import com.example.javaserver.testing.old.model.saving_result.PassedTest;
import com.example.javaserver.testing.theme.Theme;
import com.example.javaserver.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PassedTestRepo extends JpaRepository<PassedTest, Long> {
    List<PassedTest> findAllByUser(User user);
    List<PassedTest> findAllByUserAndTheme(User user, Theme theme);
}
