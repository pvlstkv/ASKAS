package com.example.javaserver.schedule.service;

import com.example.javaserver.schedule.controller.dto.*;
import com.example.javaserver.schedule.model.Schedule;
import com.example.javaserver.schedule.repo.ScheduleRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TeacherService {
    private final ScheduleRepo scheduleRepo;

    public TeacherService(ScheduleRepo scheduleRepo) {
        this.scheduleRepo = scheduleRepo;
    }

    public Collection<String> getListTeacher(){
        Collection<Schedule> schedules = (Collection<Schedule>) scheduleRepo.findAll();
        List<String> listTeacher = schedules.stream().distinct().map(schedule -> schedule.getTeacher()).collect(Collectors.toList());
        return  listTeacher;
    }

    public GroupTeacher getScheduleTeacher(String nameTeacher){
        GroupTeacher teacher = new GroupTeacher(nameTeacher);
        List<DayTeacher> days = new ArrayList<>();
        fillListDays(nameTeacher,days,1);
        fillListDays(nameTeacher,days,2);
        teacher.setDays(days);

        return teacher;
    }

    public void fillListDays(String nameTeacher, List<DayTeacher> days, int numWeek){
        for (int i = 1; i < 8; i++) {
            DayTeacher dayTeacher = new DayTeacher(i,numWeek);
            List<Schedule> scheduleList = scheduleRepo.findByTeacherAndNumberDayAndNumberWeek(nameTeacher,i,numWeek);
            List<CoupleTeacher> couples = getCouple(scheduleList);
            if(couples.size() != 0){
                dayTeacher.setCoupels(couples);
                days.add(dayTeacher);
            }
        }
    }

    public List<CoupleTeacher> getCouple(List<Schedule> scheduleList){
        List<CoupleTeacher> couples = new ArrayList<>();
        for (Schedule schedule : scheduleList){
            couples.add(extractCouple(schedule));
        }
        return couples;
    }

    public CoupleTeacher extractCouple(Schedule schedule){
        CoupleTeacher coupleTeacher = new CoupleTeacher();
        coupleTeacher.setPair_number(schedule.getPairNumber());
        coupleTeacher.setNameGroup(schedule.getNameGroup());
        coupleTeacher.setSubject(schedule.getSubject());
        coupleTeacher.setSubgroup(schedule.getSubgroup());
        coupleTeacher.setPlace(schedule.getPlace());
        coupleTeacher.setTypeSubject(schedule.getTypeSubject());
        coupleTeacher.setInfo(schedule.getInfo());
        return coupleTeacher;
    }
}
