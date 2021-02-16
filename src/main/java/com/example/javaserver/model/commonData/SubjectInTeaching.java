package com.example.javaserver.model.commonData;

import com.example.javaserver.configuration.Marks;
import lombok.Data;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Data
@Entity
public class SubjectInTeaching {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    long id;

    @OneToOne
    Subject subject;
    Marks mark;

    OffsetDateTime createdAt;
    OffsetDateTime updatedAt;
}
