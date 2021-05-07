package com.example.javaserver.common_data.service;

import com.example.javaserver.common_data.model.Faculty;
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
public class FacultyService {
    private final FacultyDataService facultyDataService;

    @Autowired
    public FacultyService(FacultyDataService facultyDataService) {
        this.facultyDataService = facultyDataService;
    }

    public Faculty create(Faculty faculty) {
        return facultyDataService.save(faculty);
    }

    public Message delete(Set<Long> ids) {
        facultyDataService.deleteAllByIdIn(ids);
        return new Message("Найденные факультеты были успешно удалены");
    }

    @SuppressWarnings("Duplicates")
    @Transactional
    public Message update(
            Long id,
            String shortName,
            String fullName
    ) {
        Faculty faculty = facultyDataService.getById(id);
        if (shortName != null) {
            try {
                faculty.setShortName(shortName.equals("null") ? null : shortName);
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Недопустимое значение поля: shortName");
            }
        }

        if (fullName != null) {
            try {
                faculty.setFullName(fullName.equals("null") ? null : fullName);
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Недопустимое значение поля: fullName");
            }
        }

        return new Message("Факультет был успешно изменён");
    }

    public Collection<Faculty> getAll() {
        return facultyDataService.findAllBy();
    }

    public Collection<Faculty> criteriaSearch(Set<SearchCriteria> criteria) {
        try {
            Specification<Faculty> specification = CommonSpecification.of(criteria);
            return facultyDataService.findAll(specification);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Критерии поиска некорректны");
        }
    }

    public Collection<Faculty> searchByIds(Set<Long> ids) {
        return facultyDataService.findAllByIdIn(ids);
    }
}
