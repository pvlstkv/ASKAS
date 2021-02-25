package com.example.javaserver.repo;

import com.example.javaserver.model.schedule.Schedule;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ScheduleRepo extends CrudRepository<Schedule, Long> {
    List<Schedule> findByNameGroup(String nameGroup);
    List<Schedule> findByNameGroupAndNumberDayAndNumberWeek(String nameGroup, Integer numberDay, Integer numberWeek);
    boolean existsByNameGroup(String nameGroup);
    @Transactional
    long deleteByNameGroup(String nameGroup);
}
