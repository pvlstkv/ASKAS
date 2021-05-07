package com.example.javaserver.common_data.service;

import com.example.javaserver.common_data.model.StudyGroup;
import com.example.javaserver.common_data.model.Subject;
import com.example.javaserver.common_data.model.SubjectSemester;
import com.example.javaserver.common_data.repo.SubjectSemesterRepo;
import com.example.javaserver.general.criteria.SearchCriteria;
import com.example.javaserver.general.model.Message;
import com.example.javaserver.general.specification.CommonSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Service
public class SubjectSemesterDataService {
    private final SubjectSemesterRepo subjectSemesterRepo;

    public SubjectSemesterDataService(SubjectSemesterRepo subjectSemesterRepo) {
        this.subjectSemesterRepo = subjectSemesterRepo;
    }

    public  Set<SubjectSemester> findSubjectSemestersByIdIn(Set<Long> subjectSemesterIds){
        return subjectSemesterRepo.findSubjectSemestersByIdIn(subjectSemesterIds);
    }

    public SubjectSemester save(SubjectSemester subjectSemester){
        return subjectSemesterRepo.save(subjectSemester);
    }

    public void deleteAllByIdIn(Set<Long> ids) {
        subjectSemesterRepo.deleteAllByIdIn(ids);
    }

    public SubjectSemester getById(Long id){
        Optional<SubjectSemester> semesterOptional = subjectSemesterRepo.findById(id);
        if (!semesterOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Семестр с указанным id не существует");
        }
        return semesterOptional.get();
    }

    public Collection<SubjectSemester> findAllBy() {
        return subjectSemesterRepo.findAllBy();
    }

    public Collection<SubjectSemester> findAll(Specification<SubjectSemester> specification){
        return subjectSemesterRepo.findAll(specification);
    }

    public Collection<SubjectSemester> findAllByIdIn(Set<Long> ids) {
        return subjectSemesterRepo.findAllByIdIn(ids);
    }

    public Collection<SubjectSemester> findAllBySubjectEqualsAndStudyGroupIn(Subject subject,Collection<StudyGroup> studyGroup) {
        return subjectSemesterRepo.findAllBySubjectEqualsAndStudyGroupIn(subject,studyGroup);
    }



}
