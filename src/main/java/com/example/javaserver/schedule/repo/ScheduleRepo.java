package com.example.javaserver.schedule.repo;

import com.example.javaserver.schedule.model.Schedule;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

public interface ScheduleRepo extends CrudRepository<Schedule, Long> {
    List<Schedule> findByNameGroup(String nameGroup);
    List<Schedule> findByNameGroupAndNumberDayAndNumberWeek(String nameGroup, Integer numberDay, Integer numberWeek);
    List<Schedule> findByTeacherAndNumberDayAndNumberWeek(String nameTeacher, Integer numberDay, Integer numberWeek);
    boolean existsByNameGroup(String nameGroup);
    boolean existsByTeacher(String nameTeacher);
    @Transactional
    long deleteByNameGroup(String nameGroup);
    Collection<Schedule> findAllByTeacher(String nameTeacher);
}
