package com.example.javaserver.schedule.service;

import com.example.javaserver.schedule.model.Schedule;
import com.example.javaserver.schedule.repo.ScheduleRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleDataService {
    private final ScheduleRepo scheduleRepo;

    public ScheduleDataService(ScheduleRepo scheduleRepo) {
        this.scheduleRepo = scheduleRepo;
    }

    public List<Schedule> findAll(){
        List<Schedule> scheduleList = (List<Schedule>) scheduleRepo.findAll();
        return scheduleList;
    }

    public List<Schedule> findByNameGroupAndNumberDayAndNumberWeek(String nameGroup, Integer numberDay, Integer numberWeek){
        List<Schedule> scheduleList = (List<Schedule>) scheduleRepo.findByNameGroupAndNumberDayAndNumberWeek(nameGroup,numberDay,numberWeek);
        return scheduleList;
    }

    public List<Schedule> findByTeacherAndNumberDayAndNumberWeek(String nameTeacher, Integer numberDay, Integer numberWeek){
        List<Schedule> scheduleList = (List<Schedule>) scheduleRepo.findByNameGroupAndNumberDayAndNumberWeek(nameTeacher,numberDay,numberWeek);
        return scheduleList;
    }

    public boolean existsByTeacher(String nameTeacher){
        return  scheduleRepo.existsByTeacher(nameTeacher);
    }

    public boolean existsByNameGroup(String nameGroup){
        return  scheduleRepo.existsByNameGroup(nameGroup);
    }

    public Long deleteByNameGroup(String nameGroup){
        return  scheduleRepo.deleteByNameGroup(nameGroup);
    }

    public Schedule save(Schedule schedule){
        return  scheduleRepo.save(schedule);
    }

}
