package com.example.javaserver.common_data.service;

import com.example.javaserver.common_data.model.Subject;
import com.example.javaserver.common_data.repo.SubjectRepo;
import com.example.javaserver.general.model.Message;
import com.example.javaserver.general.specification.CommonSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Service
public class SubjectDataService {
    private final SubjectRepo subjectRepo;

    public SubjectDataService(SubjectRepo subjectRepo) {
        this.subjectRepo = subjectRepo;
    }

    public Subject getById(Long id){
        Optional<Subject> subjectO = subjectRepo.findById(id);
        if (subjectO.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Нет предмета с указанным id");
        }
        return subjectO.get();
    }

    public Subject save(Subject subject){
        return subjectRepo.save(subject);
    }


    public void deleteAllByIdIn(Set<Long> ids) {
        subjectRepo.deleteAllByIdIn(ids);
    }

    public Collection<Subject> findAllBy() {
        return subjectRepo.findAllBy();
    }

    public Collection<Subject> findAll(Specification<Subject> specification){
        return subjectRepo.findAll(specification);
    }

    public Set<Subject> findAllByIdIn(Set<Long> ids) {
        return subjectRepo.findAllByIdIn(ids);
    }



}
