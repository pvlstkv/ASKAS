package com.example.javaserver.schedule.service;

import com.example.javaserver.general.model.Message;
import com.example.javaserver.schedule.controller.dto.Couple;
import com.example.javaserver.schedule.controller.dto.Day;
import com.example.javaserver.schedule.controller.dto.Group;
import com.example.javaserver.schedule.model.Schedule;
import com.example.javaserver.schedule.repo.ScheduleRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class ScheduleService {
    private final ScheduleRepo scheduleRepo;
    private final ParserService parserService;

    public ScheduleService(ScheduleRepo scheduleRepo, ParserService parserService) {
        this.scheduleRepo = scheduleRepo;
        this.parserService = parserService;
    }

    @Async("processExecutor")
    public void iteratingThroughGroups() {
        int part;
        int numberGroup;
        String baseUrl;
        for (int i = 1; i < 4; i++) {
            for (int j = 1; j < 120; j++) {
                part = i;
                numberGroup = j;
                baseUrl = "https://www.ulstu.ru/schedule/students/part" + part + "/" + numberGroup + ".html";
                try {
                    parserService.parserGroup(baseUrl);
                } catch (IOException e){
                    e.printStackTrace();
                    break;
                }
            }
        }
    }

    public ResponseEntity<?> getScheduleGroup(String nameGroup){
        Group group = new Group(nameGroup);
        List<Day> days = new ArrayList<>();
        fillListDays(nameGroup,days,1);
        fillListDays(nameGroup,days,2);
        group.setDays(days);
        return new ResponseEntity<>(group,HttpStatus.OK);
    }

    public ResponseEntity<?> getListScheduleGroups(){
        List<Schedule> scheduleList = (List<Schedule>) scheduleRepo.findAll();
        Set<String> res = new LinkedHashSet<>();
        for (Schedule schedule : scheduleList) {
            res.add(schedule.getNameGroup());
        }
        res.add(String.valueOf(res.size()));
        return new ResponseEntity<>(res,HttpStatus.OK);
    }

    public void fillListDays(String nameGroup, List<Day> days, int numWeek){
        for (int i = 1; i < 8; i++) {
            Day day = new Day(i,numWeek);
            List<Schedule> scheduleList = scheduleRepo.findByNameGroupAndNumberDayAndNumberWeek(nameGroup,i,numWeek);
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
