package com.example.javaserver.common_data.service;

import com.example.javaserver.common_data.controller.client_model.SubjectSemesterIn;
import com.example.javaserver.common_data.model.Subject;
import com.example.javaserver.common_data.model.SubjectSemester;
import com.example.javaserver.common_data.repo.SubjectRepo;
import com.example.javaserver.common_data.repo.SubjectSemesterRepo;
import com.example.javaserver.general.criteria.SearchCriteria;
import com.example.javaserver.general.model.Message;
import com.example.javaserver.general.specification.CommonSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SubjectSemesterService {
    private final SubjectSemesterRepo subjectSemesterRepo;
    private final SubjectRepo subjectRepo;

    @Autowired
    public SubjectSemesterService(SubjectSemesterRepo subjectSemesterRepo, SubjectRepo subjectRepo) {
        this.subjectSemesterRepo = subjectSemesterRepo;
        this.subjectRepo = subjectRepo;
    }

    @Transactional
    public ResponseEntity<?> create(SubjectSemesterIn subjectSemesterIn) {
        SubjectSemester subjectSemester = new SubjectSemester();
        subjectSemester.setControlType(subjectSemesterIn.controlType);
        subjectSemester.setHasCourseProject(subjectSemesterIn.hasCourseProject);
        subjectSemester.setHasCourseWork(subjectSemesterIn.hasCourseWork);
        subjectSemester.setNumberOfSemester(subjectSemesterIn.numberOfSemester);

        if (subjectSemesterIn.subjectId != null) {
            Optional<Subject> subject = subjectRepo.findById(subjectSemesterIn.subjectId.intValue()); // todo порешать это
            if (!subject.isPresent()) {
                return new ResponseEntity<>(new Message("Предмет с указанным id не существует"), HttpStatus.BAD_REQUEST);
            }
            subjectSemester.setSubject(subject.get());
        }

        subjectSemesterRepo.save(subjectSemester);
        return new ResponseEntity<>(new Message("Семестр предмета успешно создан"), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> delete(Set<Long> ids) {
        subjectSemesterRepo.deleteAllByIdIn(ids);
        return new ResponseEntity<>(new Message("Найденные семестры были успешно удалены"), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> setSubject(Long subjectSemesterId, Long subjectId) {
        Optional<SubjectSemester> subjectSemester = subjectSemesterRepo.findById(subjectSemesterId);
        if (!subjectSemester.isPresent()) {
            return new ResponseEntity<>(new Message("Семестр с указанным id не был существует"), HttpStatus.BAD_REQUEST);
        }

        Optional<Subject> subject = subjectRepo.findById(subjectId.intValue()); // todo здесь тоже
        if (!subject.isPresent()) {
            return new ResponseEntity<>(new Message("Предмет с указанным id не был существует"), HttpStatus.BAD_REQUEST);
        }

        subjectSemester.get().setSubject(subject.get());
        return new ResponseEntity<>(new Message("Семестр был успешно привязан к предмету"), HttpStatus.OK);
    }

    public ResponseEntity<?> getAll() {
        Collection<SubjectSemester> subjectSemesters = subjectSemesterRepo.findAllBy();
        return new ResponseEntity<>(subjectSemesters, HttpStatus.OK);
    }

    public ResponseEntity<?> criteriaSearch(Set<SearchCriteria> criteria) {
        try {
            Specification<SubjectSemester> specification = CommonSpecification.of(criteria);
            List<SubjectSemester> subjectSemesters = subjectSemesterRepo.findAll(specification);
            return new ResponseEntity<>(subjectSemesters, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new Message("Критерии поиска некорректны"), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> searchByIds(Set<Long> ids) {
        Collection<SubjectSemester> semesters = subjectSemesterRepo.findAllByIdIn(ids);
        return new ResponseEntity<>(semesters, HttpStatus.OK);
    }
}
