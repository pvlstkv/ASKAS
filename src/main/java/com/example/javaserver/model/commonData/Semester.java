package com.example.javaserver.model.commonData;

import com.example.javaserver.model.Consumer;
import lombok.Data;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Data
@Entity
public class Semester {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    long id;

    @OneToOne
    Consumer consumer;
    @OneToOne
    SubjectInTeaching subjectInTeaching;

    int numberOfSemester;

    OffsetDateTime createdAt;
    OffsetDateTime updatedAt;
}
