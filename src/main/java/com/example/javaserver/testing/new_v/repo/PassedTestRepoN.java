package com.example.javaserver.testing.new_v.repo;

import com.example.javaserver.testing.new_v.model.saving_result.PassedTestN;
import com.example.javaserver.testing.theme.Theme;
import com.example.javaserver.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PassedTestRepoN extends JpaRepository<PassedTestN, Long> {
    List<PassedTestN> findAllByUser(User user);

    List<PassedTestN> findAllByUserAndTheme(User user, Theme theme);
}
