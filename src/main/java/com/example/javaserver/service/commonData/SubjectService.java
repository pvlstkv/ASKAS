package com.example.javaserver.service.commonData;

import com.example.javaserver.model.commonData.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubjectService {
    @Autowired
    private SubjectService subjectService;

    public boolean addSubject(Subject subject) {
        subjectService.addSubject(subject);
        return true;
    }
}
