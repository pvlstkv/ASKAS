package com.example.javaserver.schedule.service;

import com.example.javaserver.schedule.model.Schedule;
import com.example.javaserver.schedule.repo.ScheduleRepo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class ParserService {
    private final ScheduleRepo scheduleRepo;

    private ArrayList<String> listHtmlPage;
    private ArrayList<String> listNameGroup;
    private List<Schedule> scheduleList;
    private final Map<String,Integer> weekStudy = new HashMap<>();
    private final Map<String,Integer> typeSubject = new HashMap<>();

    public ParserService(ScheduleRepo scheduleRepo) {
        this.scheduleRepo = scheduleRepo;
        scheduleList = new ArrayList<>();
        listNameGroup =  new ArrayList<>();
        //дни недели
        weekStudy.put("Пнд",1);
        weekStudy.put("Втр",2);
        weekStudy.put("Срд",3);
        weekStudy.put("Чтв",4);
        weekStudy.put("Птн",5);
        weekStudy.put("Сбт",6);
        weekStudy.put("Вск",7);
        //типы пар
        typeSubject.put("лек", 1);
        typeSubject.put("пр", 2);
        typeSubject.put("Лаб", 3);
    }

    public void parserGroup(String urlGroup) throws IOException {
        //При наличие доступа к станици с расписанием парсим все элементы тега р
        listHtmlPage = new ArrayList<>();
        Document document = Jsoup.connect(urlGroup).get();
        document.outputSettings(new Document.OutputSettings().prettyPrint(false));//makes html() preserve linebreaks and spacing
        document.select("br").append("\\n");
        //document.select("p").prepend("\\n\\n");
        ArrayList<String> A = new ArrayList<>();
        Elements paragraphs = document.getElementsByTag("p");
        String check;
        String keyWord = "Расписание занятий учебной группы:";
        boolean extractNamesGroups = true;
        for (Element paragraph : paragraphs) {
            check = paragraph.text();
            if(check.contains(keyWord) && extractNamesGroups){
                extractNamesGroups = false;
                int start = check.indexOf(keyWord) + keyWord.length();
                check = check.substring(start) ;
                extractNamesGroups(check);
            }
            check = check.trim().replace('.',' ').replace('-',' ');
            listHtmlPage.add(check);
        }
        scheduleForDay();
        saveAll();

    }

    public void scheduleForDay(){
        int numberWeek = 1;
        for (int i = 0; i < listHtmlPage.size(); i++) {
            String line = listHtmlPage.get(i);
            if(line.contains("Неделя: 2")){
                numberWeek = 2;
                continue;
            }
            if(weekStudy.containsKey(line)){
                i++;
                List<String> listCouples = new ArrayList<>();
                for (int j = i;j < i+8; j++) {
                    listCouples.add(listHtmlPage.get(j));
                }
                i+=8;
                parserScheduleForDay(numberWeek, weekStudy.get(line),listCouples);
                continue;
            }
        }
    }

    public void parserScheduleForDay(int numberWeek,int numberDay,List<String> listCouples){
        for (int i = 0; i < listCouples.size(); i++) {
            String couple = listCouples.get(i);
            if(couple.equals(""))
                continue;
            parserLineCouple(numberWeek,numberDay, i+1,couple);
        }
    }

    public void parserLineCouple(int numberWeek,int numberDay,int pairNumber,String line){
        String [] arrStr = line.split(Pattern.quote("\\n"));
        if(arrStr.length == 2){
            Schedule schedule = new Schedule(numberDay,numberWeek,pairNumber,line);
            getSubjectAndTypeSubjectAndSubgroup(schedule,arrStr[0]);
            getNameTeacherAndPlace(schedule,arrStr[1]);
            scheduleList.add(schedule);
        }
        else if(arrStr.length == 3){
            Schedule schedule1 = new Schedule(numberDay,numberWeek,pairNumber,line);
            Schedule schedule2 = new Schedule(numberDay,numberWeek,pairNumber,line);
            getSubjectAndTypeSubjectAndSubgroup(schedule1,arrStr[0]);
            getNameTeacherAndPlace(schedule1,arrStr[2]);
            getSubjectAndTypeSubjectAndSubgroup(schedule2,arrStr[1]);
            getNameTeacherAndPlace(schedule2,arrStr[2]);
            scheduleList.add(schedule1);
            scheduleList.add(schedule2);
        }
        else if(arrStr.length == 4){
            Schedule schedule1 = new Schedule(numberDay,numberWeek,pairNumber,line);
            Schedule schedule2 = new Schedule(numberDay,numberWeek,pairNumber,line);
            getSubjectAndTypeSubjectAndSubgroup(schedule1,arrStr[0]);
            getNameTeacherAndPlace(schedule1,arrStr[1]);
            getSubjectAndTypeSubjectAndSubgroup(schedule2,arrStr[2]);
            getNameTeacherAndPlace(schedule2,arrStr[3]);
            scheduleList.add(schedule1);
            scheduleList.add(schedule2);
        }
    }

    public void getSubjectAndTypeSubjectAndSubgroup(Schedule schedule, String s){
        //ставим по умолчанию отсутствие типы предмета и если удается найти в словаре по ключу тип предмета, то ставим это значение
        schedule.setTypeSubject(0);
        String key = "";
        for (int i = 0; i < 5; i++) {
            if(typeSubject.containsKey(key)){
                schedule.setTypeSubject(typeSubject.get(key));
                s = s.substring(i);
                break;
            }
            key+=s.charAt(i);
        }
        //получаем название предмета и если встречаем цифру, то считаем ее номером подгруппы
        schedule.setSubgroup(0);
        String nameSubject = "";
        for (int i = 0; i < s.length(); i++) {
            if(Character.isDigit(s.charAt(i))){
                int num = Character.getNumericValue(s.charAt(i));
                schedule.setSubgroup(num);
                break;
            }
            nameSubject+=s.charAt(i);
        }
        schedule.setSubject(nameSubject.trim());
    }

    public void getNameTeacherAndPlace(Schedule schedule, String s){
        //ищем преподователя
        String teacher = "";
        String place = "";
        for (int i = 0; i < s.length(); i++) {
            if(Character.isDigit(s.charAt(i))){
                for (int j = i; j < s.length(); j++) {
                    place += s.charAt(j);
                }
                break;
            }
            teacher+=s.charAt(i);
        }
        schedule.setTeacher(teacher.trim());
        schedule.setPlace(place.trim());
    }

    public void extractNamesGroups(String s){
        String [] arr  = s.split(Pattern.quote("\\n"));
        String str = arr[0];
        String name = "";
        for (int i = 0; i < str.length(); i++) {
            if(i == str.length()-1){
                listNameGroup.add(name.trim());
                name = "";
            }
            if(str.charAt(i) != ','){
                name+=str.charAt(i);
            }
            else {
                listNameGroup.add(name.trim());
                name = "";
            }
        }
    }

    @Transactional
    public void deleteGroup(String nameGroup){
        scheduleRepo.deleteByNameGroup(nameGroup);
    }

    public void saveAll(){
        for (int i = 0; i < listNameGroup.size(); i++) {
            String nameGroup = listNameGroup.get(i);
            if(scheduleRepo.existsByNameGroup(nameGroup)){
                deleteGroup(nameGroup);
            }
            for (int j = 0; j < scheduleList.size(); j++) {
                Schedule schedule = new Schedule(scheduleList.get(j));
                schedule.setNameGroup(nameGroup);
                scheduleRepo.save(schedule);
            }
        }
        scheduleList = new ArrayList<>();
        listNameGroup =  new ArrayList<>();
    }
}