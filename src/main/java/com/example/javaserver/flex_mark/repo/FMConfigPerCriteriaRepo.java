package com.example.javaserver.flex_mark.repo;

import com.example.javaserver.flex_mark.model.FMConfigPerCriteria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FMConfigPerCriteriaRepo extends JpaRepository<FMConfigPerCriteria, Long> {
}
