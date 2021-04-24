package com.example.javaserver.common_data.service;

import com.example.javaserver.common_data.model.Department;
import com.example.javaserver.common_data.model.Faculty;
import com.example.javaserver.common_data.repo.FacultyRepo;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FacultyDataService {
    private final FacultyRepo facultyRepo;

    public FacultyDataService(FacultyRepo facultyRepo) {
        this.facultyRepo = facultyRepo;
    }

    public Faculty getById(Long id){
        Optional<Faculty> facultyOptional = facultyRepo.findById(id);
        if (!facultyOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ошибка изменения кафедры. Факультет с указанным id не существует");
        }
        return  facultyOptional.get();
    }

    public Faculty save(Faculty faculty){
        return facultyRepo.save(faculty);
    }

    public void deleteAllByIdIn(Set<Long> ids){
        facultyRepo.deleteAllByIdIn(ids);
    }

    public Collection<Faculty> findAllBy(){
        return facultyRepo.findAllBy();
    }

    public Collection<Faculty> findAll(Specification<Faculty> specification){
        return facultyRepo.findAll(specification);
    }

    public Set<Faculty> getByIds(Set<Long> ids){
        Set<Faculty> faculties =  facultyRepo.findAllByIdIn(ids);
        if (faculties.size() == ids.size()) {
            return faculties;
        } else {
            Collection<Long> foundIds = faculties.stream()
                    .map(Faculty::getId)
                    .collect(Collectors.toSet());
            Collection<Long> notFoundIds = ids.stream()
                    .filter(i -> !foundIds.contains(i))
                    .collect(Collectors.toSet());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "факультетов с id: " + Arrays.toString(notFoundIds.toArray()) + " не существуют");
        }
    }

    public Collection<Faculty> findAllByIdIn(Set<Long> ids){
        return facultyRepo.findAllByIdIn(ids);
    }
}
