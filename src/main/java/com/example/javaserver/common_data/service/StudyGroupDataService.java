package com.example.javaserver.common_data.service;

import com.example.javaserver.common_data.model.StudyGroup;
import com.example.javaserver.common_data.repo.StudyGroupRepo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class StudyGroupDataService {
    private final StudyGroupRepo studyGroupRepo;

    public StudyGroupDataService(StudyGroupRepo studyGroupRepo) {
        this.studyGroupRepo = studyGroupRepo;
    }

    public boolean existsByShortName(String shortName){
        return studyGroupRepo.existsByShortName(shortName);
    }

    public Set<StudyGroup> getAll() {
        Iterable<StudyGroup> groupIterable = studyGroupRepo.findAll();
        return StreamSupport.stream(groupIterable.spliterator(), false)
                .collect(Collectors.toSet());
    }

    public StudyGroup findByIdEquals(Long id){
        Optional<StudyGroup> group = studyGroupRepo.findByIdEquals(id);
        if (group.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Группы с указанным id не существует");
        }
        return group.get();
    }

    public Set<StudyGroup> findAllByIdIn(Set<Long> ids){
        return studyGroupRepo.findAllByIdIn(ids);
    }

    public StudyGroup getById(Long id){
        Optional<StudyGroup> group = studyGroupRepo.findById(id);
        if (group.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Учебная группа с указанным id не найдена");
        }
        return group.get();
    }

    public StudyGroup save(StudyGroup studyGroup){
        return  studyGroupRepo.save(studyGroup);
    }
}
