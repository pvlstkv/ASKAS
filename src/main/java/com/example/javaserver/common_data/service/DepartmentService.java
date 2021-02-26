package com.example.javaserver.common_data.service;

import com.example.javaserver.common_data.model.Department;
import com.example.javaserver.common_data.repo.DepartmentRepo;
import com.example.javaserver.general.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class DepartmentService {
    private final DepartmentRepo departmentRepo;

    @Autowired
    public DepartmentService(DepartmentRepo departmentRepo) {
        this.departmentRepo = departmentRepo;
    }

    @Transactional
    public ResponseEntity<?> create(Department department) {
        departmentRepo.save(department);
        return new ResponseEntity<>(new Message("Кафедра успешно создана"), HttpStatus.OK);
    }
}
