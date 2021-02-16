package com.example.javaserver.model.commonData;

import com.example.javaserver.configuration.SubjectControlType;
import lombok.Data;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Data
@Entity
public class SubjectSemester {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    long id;

    int numberOfSemester;
    SubjectControlType controlType;
    boolean hasCourseWork;
    boolean hasCourseProject;

    @ManyToOne(fetch = FetchType.LAZY)
    Subject subject;

    OffsetDateTime createdAt;
    OffsetDateTime updatedAt;

    // in the nearest future something like this
    // int countOfLecture;
    // int countOfLabWork;

}
