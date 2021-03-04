package com.example.javaserver.common_data.model;



import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.Set;

@SuppressWarnings("unused")
@Entity

@Table(name = "subject_semesters")
public class SubjectSemester {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Integer numberOfSemester;

    private SubjectControlType controlType;

    Boolean hasCourseWork;

    Boolean hasCourseProject;

    @JsonProperty("subjectId")
    @JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
    @JsonIdentityReference(alwaysAsId=true)
    @ManyToOne(fetch = FetchType.LAZY)
    private Subject subject;

    @JsonProperty("studentGroupIds")
    @JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
    @JsonIdentityReference(alwaysAsId=true)
    @ManyToMany(mappedBy = "subjectSemesters")
    private Set<StudyGroup> studyGroups;

    OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    // in the nearest future something like this
    // int countOfLecture;
    // int countOfLabWork;

    public SubjectSemester() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumberOfSemester() {
        return numberOfSemester;
    }

    public void setNumberOfSemester(Integer numberOfSemester) {
        this.numberOfSemester = numberOfSemester;
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

    public Set<StudyGroup> getStudyGroups() {
        return studyGroups;
    }

    public void setStudyGroups(Set<StudyGroup> studyGroups) {
        this.studyGroups = studyGroups;
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
}
