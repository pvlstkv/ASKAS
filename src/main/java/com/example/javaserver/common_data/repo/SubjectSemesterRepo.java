package com.example.javaserver.common_data.repo;

import com.example.javaserver.common_data.model.SubjectSemester;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Set;

public interface SubjectSemesterRepo extends CrudRepository<SubjectSemester, Long> {
    Set<SubjectSemester> findSubjectSemestersByIdIn(Set<Long> ids);
    void deleteAllByIdIn(Collection<Long> ids);
}