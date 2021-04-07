package com.example.javaserver.common_data.repo;

import com.example.javaserver.common_data.model.StudyGroup;
import com.example.javaserver.common_data.model.Subject;
import com.example.javaserver.common_data.model.SubjectSemester;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Set;

public interface SubjectSemesterRepo extends
        CrudRepository<SubjectSemester, Long>,
        JpaSpecificationExecutor<SubjectSemester>
{
    Set<SubjectSemester> findSubjectSemestersByIdIn(Set<Long> ids);
    @Transactional
    void deleteAllByIdIn(Collection<Long> ids);
    Collection<SubjectSemester> findAllByIdIn(Set<Long> ids);
    Collection<SubjectSemester> findAllBy();
    Collection<SubjectSemester> findAllBySubjectEquals(Subject subject);
    Collection<SubjectSemester> findAllByIdIn(Collection<Long> ids);
    Collection<SubjectSemester> findAllBySubjectEqualsAndStudyGroupIn(Subject subject, Collection<StudyGroup> studyGroups);
}