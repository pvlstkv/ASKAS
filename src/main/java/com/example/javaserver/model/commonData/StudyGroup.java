package com.example.javaserver.model.commonData;

import com.example.javaserver.model.Consumer;
import lombok.Data;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
//@Table(name = "")
public class StudyGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    long id;

    Integer code;
    Integer groupNumber;
    String shortName;
    String fullName;
    OffsetDateTime createdAt;
    OffsetDateTime updatedAt;
    Integer yearOfStudyStart;

    @ManyToOne(fetch = FetchType.LAZY)
    Department department;
    @OneToMany(mappedBy = "studyGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Consumer> students = new ArrayList<>();

}
