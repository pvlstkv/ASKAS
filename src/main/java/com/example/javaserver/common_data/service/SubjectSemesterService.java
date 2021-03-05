package com.example.javaserver.common_data.service;

import com.example.javaserver.common_data.controller.client_model.SubjectSemesterIn;
import com.example.javaserver.common_data.model.Subject;
import com.example.javaserver.common_data.model.SubjectControlType;
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
            Optional<Subject> subject = subjectRepo.findById(subjectSemesterIn.subjectId);
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

    @SuppressWarnings("Duplicates")
    @Transactional
    public ResponseEntity<?> update(
            Long id,
            String controlType,
            String hasCourseProject,
            String hasCourseWork,
            String numberOfSemester,
            String subjectId
    ) {
        Optional<SubjectSemester> semesterOptional = subjectSemesterRepo.findById(id);
        if (!semesterOptional.isPresent()) {
            return new ResponseEntity<>(new Message("Семестр с указанным id не существует"), HttpStatus.BAD_REQUEST);
        }
        SubjectSemester semester = semesterOptional.get();

        if (controlType != null) {
            try {
                semester.setControlType(controlType.equals("null") ? null : SubjectControlType.valueOf(controlType));
            } catch (Exception e) {
                return new ResponseEntity<>(new Message("Недопустимое значение поля: controlType"), HttpStatus.BAD_REQUEST);
            }
        }

        if (hasCourseProject != null) {
            try {
                semester.setHasCourseProject(hasCourseProject.equals("null") ? null : Boolean.parseBoolean(hasCourseProject));
            } catch (Exception e) {
                return new ResponseEntity<>(new Message("Недопустимое значение поля: hasCourseProject"), HttpStatus.BAD_REQUEST);
            }
        }

        if (hasCourseWork != null) {
            try {
                semester.setHasCourseWork(hasCourseWork.equals("null") ? null : Boolean.parseBoolean(hasCourseWork));
            } catch (Exception e) {
                return new ResponseEntity<>(new Message("Недопустимое значение поля: hasCourseWork"), HttpStatus.BAD_REQUEST);
            }
        }

        if (numberOfSemester != null) {
            try {
                semester.setNumberOfSemester(numberOfSemester.equals("null") ? null : Integer.parseInt(numberOfSemester));
            } catch (Exception e) {
                return new ResponseEntity<>(new Message("Недопустимое значение поля: hasCourseWork"), HttpStatus.BAD_REQUEST);
            }
        }

        if (subjectId != null) {
            Subject subject = null;
            if (!subjectId.equals("null")) {
                Optional<Subject> subjectOptional;
                try {
                    subjectOptional = subjectRepo.findById(Long.parseLong(subjectId));
                } catch (Exception e) {
                    return new ResponseEntity<>(new Message("Ошибка изменения семестра. Недопустимый id предмета"), HttpStatus.BAD_REQUEST);
                }
                if (!subjectOptional.isPresent()) {
                    return new ResponseEntity<>(new Message("Ошибка изменения семестра. Предмет с указанным id не существует"), HttpStatus.BAD_REQUEST);
                }
                subject = subjectOptional.get();
            }
            semester.setSubject(subject);
        }

        return new ResponseEntity<>(new Message("Семестр был успешно изменён"), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> setSubject(Long subjectSemesterId, Long subjectId) {
        Optional<SubjectSemester> subjectSemester = subjectSemesterRepo.findById(subjectSemesterId);
        if (!subjectSemester.isPresent()) {
            return new ResponseEntity<>(new Message("Семестр с указанным id не был существует"), HttpStatus.BAD_REQUEST);
        }

        Optional<Subject> subject = subjectRepo.findById(subjectId);
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
