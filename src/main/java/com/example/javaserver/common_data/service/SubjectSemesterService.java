package com.example.javaserver.common_data.service;

import com.example.javaserver.common_data.controller.client_model.SubjectSemesterIn;
import com.example.javaserver.common_data.model.StudyGroup;
import com.example.javaserver.common_data.model.Subject;
import com.example.javaserver.common_data.model.SubjectControlType;
import com.example.javaserver.common_data.model.SubjectSemester;
import com.example.javaserver.common_data.repo.StudyGroupRepo;
import com.example.javaserver.common_data.repo.SubjectRepo;
import com.example.javaserver.common_data.repo.SubjectSemesterRepo;
import com.example.javaserver.general.criteria.SearchCriteria;
import com.example.javaserver.general.model.Message;
import com.example.javaserver.general.specification.CommonSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Service
public class SubjectSemesterService {
    private final SubjectSemesterRepo subjectSemesterRepo;
    private final SubjectRepo subjectRepo;
    private final StudyGroupRepo studyGroupRepo;

    @Autowired
    public SubjectSemesterService(SubjectSemesterRepo subjectSemesterRepo, SubjectRepo subjectRepo, StudyGroupRepo studyGroupRepo) {
        this.subjectSemesterRepo = subjectSemesterRepo;
        this.subjectRepo = subjectRepo;
        this.studyGroupRepo = studyGroupRepo;
    }

    public Message create(SubjectSemesterIn subjectSemesterIn) {
        SubjectSemester subjectSemester = new SubjectSemester();
        subjectSemester.setControlType(subjectSemesterIn.controlType);
        subjectSemester.setHasCourseProject(subjectSemesterIn.hasCourseProject);
        subjectSemester.setHasCourseWork(subjectSemesterIn.hasCourseWork);

        if (subjectSemesterIn.subjectId != null) {
            Optional<Subject> subject = subjectRepo.findById(subjectSemesterIn.subjectId);
            if (!subject.isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Предмет с указанным id не существует");
            }
            subjectSemester.setSubject(subject.get());
        }

        subjectSemesterRepo.save(subjectSemester);
        return new Message("Семестр предмета успешно создан");
    }

    public Message delete(Set<Long> ids) {
        subjectSemesterRepo.deleteAllByIdIn(ids);
        return new Message("Найденные семестры были успешно удалены");
    }

    @SuppressWarnings("Duplicates")
    @Transactional
    public Message update(
            Long id,
            String controlType,
            String hasCourseProject,
            String hasCourseWork,
            String subjectId
    ) {
        Optional<SubjectSemester> semesterOptional = subjectSemesterRepo.findById(id);
        if (!semesterOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Семестр с указанным id не существует");
        }
        SubjectSemester semester = semesterOptional.get();

        if (controlType != null) {
            try {
                semester.setControlType(controlType.equals("null") ? null : SubjectControlType.valueOf(controlType));
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Недопустимое значение поля: controlType");
            }
        }

        if (hasCourseProject != null) {
            try {
                semester.setHasCourseProject(hasCourseProject.equals("null") ? null : Boolean.parseBoolean(hasCourseProject));
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Недопустимое значение поля: hasCourseProject");
            }
        }

        if (hasCourseWork != null) {
            try {
                semester.setHasCourseWork(hasCourseWork.equals("null") ? null : Boolean.parseBoolean(hasCourseWork));
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Недопустимое значение поля: hasCourseWork");
            }
        }

        if (subjectId != null) {
            Subject subject = null;
            if (!subjectId.equals("null")) {
                Optional<Subject> subjectOptional;
                try {
                    subjectOptional = subjectRepo.findById(Long.parseLong(subjectId));
                } catch (Exception e) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ошибка изменения семестра. Недопустимый id предмета");
                }
                if (!subjectOptional.isPresent()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ошибка изменения семестра. Предмет с указанным id не существует");
                }
                subject = subjectOptional.get();
            }
            semester.setSubject(subject);
        }

        return new Message("Семестр был успешно изменён");
    }

    @Transactional
    public Message setSubject(Long subjectSemesterId, Long subjectId) {
        Optional<SubjectSemester> subjectSemester = subjectSemesterRepo.findById(subjectSemesterId);
        if (!subjectSemester.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Семестр с указанным id не был существует");
        }

        Optional<Subject> subject = subjectRepo.findById(subjectId);
        if (!subject.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Предмет с указанным id не был существует");
        }

        subjectSemester.get().setSubject(subject.get());

        return new Message("Семестр был успешно привязан к предмету");
    }

    public Collection<SubjectSemester> getAll() {
        return subjectSemesterRepo.findAllBy();
    }

    public Collection<SubjectSemester> criteriaSearch(Set<SearchCriteria> criteria) {
        try {
            Specification<SubjectSemester> specification = CommonSpecification.of(criteria);
            return subjectSemesterRepo.findAll(specification);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Критерии поиска некорректны");
        }
    }

    public Collection<SubjectSemester> searchByIds(Set<Long> ids) {
        return subjectSemesterRepo.findAllByIdIn(ids);
    }

    public Collection<SubjectSemester> searchBySubjectIdAndGroupIds(Long subjectId, Set<Long> groupIds) {
        Optional<Subject> subjectO = subjectRepo.findById(subjectId);
        if (!subjectO.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Предмет с указанным id не существует");
        }

        Collection<StudyGroup> groups = studyGroupRepo.findAllByIdIn(groupIds);
        for (Long id : groupIds) {
            if (groups.stream().noneMatch(g -> g.getId().equals(id))) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Семестр с id = " + id + " не существует");
            }
        }

        return subjectSemesterRepo.findAllBySubjectEqualsAndStudyGroupIn(subjectO.get(), groups);
    }
}
