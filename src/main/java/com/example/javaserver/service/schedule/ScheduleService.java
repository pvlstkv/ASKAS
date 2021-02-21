package com.example.javaserver.service.schedule;

import com.example.javaserver.controller.schedule.model.Couple;
import com.example.javaserver.controller.schedule.model.Day;
import com.example.javaserver.controller.schedule.model.Group;
import com.example.javaserver.model.User;
import com.example.javaserver.model.schedule.Schedule;
import com.example.javaserver.repo.ScheduleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleService {
    @Autowired
    private ScheduleRepo scheduleRepo;

    public ResponseEntity<?> getScheduleGroup(String nameGroup){
        Group group = new Group(nameGroup);
        List<Day> days = new ArrayList<>();
        fillListDays(nameGroup,days,1);
        fillListDays(nameGroup,days,2);
        group.setDays(days);
        return new ResponseEntity<>(group,HttpStatus.OK);
    }
    public void fillListDays(String nameGroup, List<Day> days, int numWeek){
        for (int i = 1; i < 8; i++) {
            Day day = new Day(i,numWeek);
            List<Schedule> scheduleList = scheduleRepo.findByNameGroupAndAndNumberDayAndAndNumberWeek(nameGroup,i,numWeek);
            List<Couple> couples = getCouple(scheduleList);
            if(couples.size() != 0){
                day.setCoupels(couples);
                days.add(day);
            }
        }
    }

    public List<Couple> getCouple(List<Schedule> scheduleList){
        List<Couple> couples = new ArrayList<>();
        for (Schedule schedule : scheduleList){
            couples.add(extractCouple(schedule));
        }
        return couples;
    }

    public Couple extractCouple(Schedule schedule){
        Couple couple = new Couple();
        couple.setPair_number(schedule.getPairNumber());
        couple.setTeacher(schedule.getTeacher());
        couple.setSubject(schedule.getSubject());
        couple.setSubgroup(schedule.getSubgroup());
        couple.setPlace(schedule.getPlace());
        couple.setTypeSubject(schedule.getTypeSubject());
        couple.setInfo(schedule.getInfo());
        return couple;
    }

}
