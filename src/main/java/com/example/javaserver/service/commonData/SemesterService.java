package com.example.javaserver.service.commonData;

import com.example.javaserver.model.Consumer;
import com.example.javaserver.model.commonData.Semester;
import com.example.javaserver.model.commonData.Subject;
import com.example.javaserver.repository.commonData.SemesterRepo;
import com.example.javaserver.repository.commonData.SubjectRepo;
import com.example.javaserver.repository.commonData.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class SemesterService {
    @Autowired
    private SemesterService semesterService;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private SubjectRepo subjectRepo;
    @Autowired
    private SemesterRepo semesterRepo;

    public boolean startStudying(Long consumerId) {
        Optional<Consumer> optionalConsumer = userRepo.findById(consumerId);
        if (!optionalConsumer.isPresent()) {
            return false;
        }
        Consumer consumer = optionalConsumer.get();
        int numberOfSemester = consumer.getNumberOfSemester();
        List<Subject> subjectsInCurrentSemester = subjectRepo.findAllByNumberOfSemester(numberOfSemester);
        if (subjectsInCurrentSemester.isEmpty())
            return false;
        List<Semester> semesterUnits = new ArrayList<>();
        for (Subject subjectItem : subjectsInCurrentSemester) {
            semesterUnits.add(new Semester(consumer, subjectItem, numberOfSemester, true));
        }
        semesterRepo.saveAll(semesterUnits);
        return true;
    }
}
