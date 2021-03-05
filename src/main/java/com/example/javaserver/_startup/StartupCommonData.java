package com.example.javaserver._startup;


import com.example.javaserver.common_data.model.*;
import com.example.javaserver.common_data.repo.DepartmentRepo;
import com.example.javaserver.common_data.repo.FacultyRepo;
import com.example.javaserver.common_data.repo.SubjectRepo;
import com.example.javaserver.common_data.repo.SubjectSemesterRepo;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class StartupCommonData implements ApplicationListener<ContextRefreshedEvent> {
    private final FacultyRepo facultyRepo;
    private final DepartmentRepo departmentRepo;
    private final SubjectRepo subjectRepo;
    private final SubjectSemesterRepo semesterRepo;

    public StartupCommonData(FacultyRepo facultyRepo, DepartmentRepo departmentRepo, SubjectRepo subjectRepo, SubjectSemesterRepo semesterRepo) {
        this.facultyRepo = facultyRepo;
        this.departmentRepo = departmentRepo;
        this.subjectRepo = subjectRepo;
        this.semesterRepo = semesterRepo;
    }

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        Faculty faculty1 = new Faculty();
        faculty1.setShortName("ФИСТ");
        faculty1.setFullName("Факультет информационных систем и технологий");
        facultyRepo.save(faculty1);

        Faculty faculty2 = new Faculty();
        faculty2.setShortName("РадиоФак");
        faculty2.setFullName("Радиотехнический факультет");
        facultyRepo.save(faculty2);

        Department department1 = new Department();
        department1.setShortName("ВТ");
        department1.setFullName("Вычислительная техника");
        department1.setFaculty(faculty1);
        departmentRepo.save(department1);

        Department department2 = new Department();
        department2.setShortName("Физика");
        department2.setFullName("Физика");
        department2.setFaculty(faculty2);
        departmentRepo.save(department2);

        Subject subject1 = new Subject();
        subject1.setName("СЭО");
        subject1.setDecryption("Средства электронного обучения");
        subject1.setDepartment(department1);
        subjectRepo.save(subject1);

        Subject subject2 = new Subject();
        subject2.setName("ПФРСАПР");
        subject2.setDecryption("Программирование функциональных расширений САПР");
        subject2.setDepartment(department1);
        subjectRepo.save(subject2);

        Subject subject3 = new Subject();
        subject3.setName("Физика");
        subject3.setDecryption("Физика");
        subject3.setDepartment(department2);
        subjectRepo.save(subject3);

        Subject subject4 = new Subject();
        subject4.setName("Наука жизни");
        subject4.setDecryption("Философия");
        subjectRepo.save(subject4);

        SubjectSemester semester1 = new SubjectSemester();
        semester1.setSubject(subject1);
        semester1.setNumberOfSemester(1);
        semester1.setControlType(SubjectControlType.CREDIT);
        semester1.setHasCourseWork(false);
        semester1.setHasCourseProject(false);
        semesterRepo.save(semester1);

        SubjectSemester semester2 = new SubjectSemester();
        semester2.setSubject(subject1);
        semester2.setNumberOfSemester(2);
        semester2.setControlType(SubjectControlType.EXAM);
        semester2.setHasCourseWork(true);
        semester2.setHasCourseProject(false);
        semesterRepo.save(semester2);

        SubjectSemester semester3 = new SubjectSemester();
        semester3.setSubject(subject2);
        semester3.setNumberOfSemester(1);
        semester3.setControlType(SubjectControlType.EXAM);
        semester3.setHasCourseWork(true);
        semester3.setHasCourseProject(true);
        semesterRepo.save(semester3);

        SubjectSemester semester4 = new SubjectSemester();
        semester4.setSubject(subject3);
        semester4.setNumberOfSemester(1);
        semester4.setControlType(SubjectControlType.DIFFERENTIAL_CREDIT);
        semester4.setHasCourseWork(false);
        semester4.setHasCourseProject(true);
        semesterRepo.save(semester4);

        SubjectSemester semester5 = new SubjectSemester();
        semester5.setSubject(subject4);
        semester5.setNumberOfSemester(1);
        semester5.setControlType(SubjectControlType.CREDIT);
        semester5.setHasCourseWork(false);
        semester5.setHasCourseProject(false);
        semesterRepo.save(semester5);

        SubjectSemester semester6 = new SubjectSemester();
        semester6.setSubject(subject4);
        semester6.setNumberOfSemester(2);
        semester6.setControlType(SubjectControlType.EXAM);
        semester6.setHasCourseWork(true);
        semester6.setHasCourseProject(false);
        semesterRepo.save(semester6);
    }
}
