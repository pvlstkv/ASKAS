package com.example.javaserver.common_data.service;

import com.example.javaserver.common_data.controller.client_model.FacultyIn;
import com.example.javaserver.common_data.model.Faculty;
import com.example.javaserver.common_data.repo.FacultyRepo;
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
public class FacultyService {
    private final FacultyRepo facultyRepo;

    @Autowired
    public FacultyService(FacultyRepo facultyRepo) {
        this.facultyRepo = facultyRepo;
    }

    public Message create(FacultyIn facultyIn) {
        if (facultyIn.shortName == null || facultyIn.fullName == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Факультет должен иметь короткое и полное имя");
        }

        Faculty faculty = new Faculty();
        faculty.setShortName(facultyIn.shortName);
        faculty.setFullName(facultyIn.fullName);

        facultyRepo.save(faculty);
        return new Message("Факультет успешно создан");
    }

    public Message delete(Set<Long> ids) {
        facultyRepo.deleteAllByIdIn(ids);
        return new Message("Найденные факультеты были успешно удалены");
    }

    @SuppressWarnings("Duplicates")
    @Transactional
    public Message update(
            Long id,
            String shortName,
            String fullName
    ) {
        Optional<Faculty> facultyOptional = facultyRepo.findById(id);
        if (!facultyOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Факультет с указанным id не существует");
        }
        Faculty faculty = facultyOptional.get();

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
        return facultyRepo.findAllBy();
    }

    public Collection<Faculty> criteriaSearch(Set<SearchCriteria> criteria) {
        try {
            Specification<Faculty> specification = CommonSpecification.of(criteria);
            return facultyRepo.findAll(specification);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Критерии поиска некорректны");
        }
    }

    public Collection<Faculty> searchByIds(Set<Long> ids) {
        return facultyRepo.findAllByIdIn(ids);
    }
}
