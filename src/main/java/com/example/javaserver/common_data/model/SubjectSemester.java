package com.example.javaserver.common_data.model;


import com.example.javaserver.study.model.Literature;
import com.example.javaserver.study.model.Task;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.Set;

@SuppressWarnings("unused")
@Entity
@Table(name = "subject_semesters")
public class SubjectSemester {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private SubjectControlType controlType;
    private Boolean hasCourseWork;
    private Boolean hasCourseProject;

    @ManyToMany(mappedBy = "semesters")
    private Set<Task> tasks;

    @ManyToMany(mappedBy = "semesters")
    private Set<Literature> literature;

    @ManyToOne(fetch = FetchType.EAGER)
    private Subject subject;

    @ManyToOne(fetch = FetchType.EAGER)
    private StudyGroup studyGroup;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    @OneToMany(mappedBy = "subjectSemester", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SemesterMark> semesterMarks;

    // in the nearest future something like this
    // int countOfLecture;
    // int countOfLabWork;

    public SubjectSemester() { }

    public Set<SemesterMark> getSemesterMark() {
        return semesterMarks;
    }

    public void setSemesterMark(Set<SemesterMark> semesterMarks) {
        this.semesterMarks = semesterMarks;
    }

    public Set<SemesterMark> getSemesterMarks() {
        return semesterMarks;
    }

    public void setSemesterMarks(Set<SemesterMark> semesterMarks) {
        this.semesterMarks = semesterMarks;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SubjectControlType getControlType() {
        return controlType;
    }

    public void setControlType(SubjectControlType controlType) {
        this.controlType = controlType;
    }

    public Boolean getHasCourseWork() {
        return hasCourseWork;
    }

    public void setHasCourseWork(Boolean hasCourseWork) {
        this.hasCourseWork = hasCourseWork;
    }

    public Boolean getHasCourseProject() {
        return hasCourseProject;
    }

    public void setHasCourseProject(Boolean hasCourseProject) {
        this.hasCourseProject = hasCourseProject;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public StudyGroup getStudyGroup() {
        return studyGroup;
    }

    public void setStudyGroup(StudyGroup studyGroup) {
        this.studyGroup = studyGroup;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Literature> getLiterature() {
        return literature;
    }

    public void setLiterature(Set<Literature> literature) {
        this.literature = literature;
    }
}
