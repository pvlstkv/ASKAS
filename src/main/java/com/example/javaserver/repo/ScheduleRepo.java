package com.example.javaserver.repo;

import com.example.javaserver.model.UserFile;
import com.example.javaserver.model.schedule.Schedule;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ScheduleRepo extends CrudRepository<Schedule, Integer> {
    List<Schedule> findByNameGroup(String s);
    List<Schedule> findByNameGroupAndAndNumberDayAndAndNumberWeek(String nameGroup,Integer numberDay,Integer numberWeek);
}
