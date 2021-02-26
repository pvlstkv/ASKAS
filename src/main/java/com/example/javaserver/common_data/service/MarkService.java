package com.example.javaserver.common_data.service;

import com.example.javaserver.common_data.model.Mark;
import com.example.javaserver.common_data.repo.MarkRepo;
import com.example.javaserver.general.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class MarkService {
    private final MarkRepo markRepo;

    @Autowired
    public MarkService(MarkRepo markRepo) {
        this.markRepo = markRepo;
    }

    @Transactional
    public ResponseEntity<?> create(Mark mark) {
        markRepo.save(mark);
        return new ResponseEntity<>(new Message("Оценка успешно создана"), HttpStatus.OK);
    }
}
