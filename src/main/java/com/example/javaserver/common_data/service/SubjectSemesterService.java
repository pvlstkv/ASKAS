package com.example.javaserver.common_data.service;

import com.example.javaserver.common_data.model.StudyGroup;
import com.example.javaserver.common_data.model.Subject;
import com.example.javaserver.common_data.model.SubjectControlType;
import com.example.javaserver.common_data.model.SubjectSemester;
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
import java.util.Set;

@Service
public class SubjectSemesterService {
    private final SubjectSemesterDataService subjectSemesterDataService;
    private final SubjectDataService subjectDataService;
    private final StudyGroupDataService studyGroupDataService;

    @Autowired
    public SubjectSemesterService(SubjectSemesterDataService subjectSemesterDataService, SubjectDataService subjectDataService, StudyGroupDataService studyGroupDataService) {
        this.subjectSemesterDataService = subjectSemesterDataService;
        this.subjectDataService = subjectDataService;
        this.studyGroupDataService = studyGroupDataService;
    }

    public SubjectSemester create(SubjectSemester subjectSemester) {
        return  subjectSemesterDataService.save(subjectSemester);
    }

    @Transactional
    public Message delete(Set<Long> ids) {
        subjectSemesterDataService.deleteAllByIdIn(ids);
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
        SubjectSemester semester = subjectSemesterDataService.getById(id);
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
               Subject subjectOptional;
                try {
                    subjectOptional = subjectDataService.getById(Long.parseLong(subjectId));
                } catch (Exception e) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ошибка изменения семестра. Недопустимый id предмета");
                }
                subject = subjectOptional;
            }
            semester.setSubject(subject);
        }

        return new Message("Семестр был успешно изменён");
    }

    @Transactional
    public Message setSubject(Long subjectSemesterId, Long subjectId) {
      SubjectSemester subjectSemester = subjectSemesterDataService.getById(subjectSemesterId);
        Subject subject = subjectDataService.getById(subjectId);
        subjectSemester.setSubject(subject);
        return new Message("Семестр был успешно привязан к предмету");
    }

    public Collection<SubjectSemester> getAll() {
        return subjectSemesterDataService.findAllBy();
    }

    public Collection<SubjectSemester> criteriaSearch(Set<SearchCriteria> criteria) {
        try {
            Specification<SubjectSemester> specification = CommonSpecification.of(criteria);
            return subjectSemesterDataService.findAll(specification);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Критерии поиска некорректны");
        }
    }

    public Collection<SubjectSemester> searchByIds(Set<Long> ids) {
        return subjectSemesterDataService.findAllByIdIn(ids);
    }

    public Collection<SubjectSemester> searchBySubjectIdAndGroupIds(Long subjectId, Set<Long> groupIds) {
        Subject subjectO = subjectDataService.getById(subjectId);

        Collection<StudyGroup> groups = studyGroupDataService.findAllByIdIn(groupIds);
        for (Long id : groupIds) {
            if (groups.stream().noneMatch(g -> g.getId().equals(id))) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Семестр с id = " + id + " не существует");
            }
        }
        return subjectSemesterDataService.findAllBySubjectEqualsAndStudyGroupIn(subjectO, groups);
    }
}
