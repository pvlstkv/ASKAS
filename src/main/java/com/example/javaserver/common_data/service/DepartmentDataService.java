package com.example.javaserver.common_data.service;

import com.example.javaserver.common_data.model.Department;
import com.example.javaserver.common_data.repo.DepartmentRepo;
import com.example.javaserver.study.model.Literature;
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
public class DepartmentDataService {
    private final DepartmentRepo departmentRepo;

    public DepartmentDataService(DepartmentRepo departmentRepo) {
        this.departmentRepo = departmentRepo;
    }

    public Department save(Department department){
        return departmentRepo.save(department);
    }

    public void deleteAllByIdIn(Set<Long> ids){
        departmentRepo.deleteAllByIdIn(ids);
    }

    public Department getById(Long id){
        Optional<Department> departmentOptional = departmentRepo.findById(id);
        if (!departmentOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Кафедра с указанным id не существует");
        }
        Department department = departmentOptional.get();
        return department;
    }

    public Set<Department> getByIds(Set<Long> ids){
        Set<Department> departments =  departmentRepo.findAllByIdIn(ids);
        if (departments.size() == ids.size()) {
            return departments;
        } else {
            Collection<Long> foundIds = departments.stream()
                    .map(Department::getId)
                    .collect(Collectors.toSet());
            Collection<Long> notFoundIds = ids.stream()
                    .filter(i -> !foundIds.contains(i))
                    .collect(Collectors.toSet());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "департаментов с id: " + Arrays.toString(notFoundIds.toArray()) + " не существуют");
        }
    }

    public Collection<Department> findAll(){
        return departmentRepo.findAllBy();
    }

    public Collection<String> getAllShortNames() {
        return departmentRepo
                .findAllBy()
                .stream()
                .map(Department::getShortName)
                .collect(Collectors.toSet());
    }

    public Collection<Department> findAll(Specification<Department> specification){
        return departmentRepo.findAll(specification);
    }

    public Collection<Department> findAllByIdIn(Set<Long> ids) {
        return departmentRepo.findAllByIdIn(ids);
    }



}
