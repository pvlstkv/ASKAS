package com.example.javaserver.testService.models.saving_results;

import com.example.javaserver.testService.models.Question;
import com.example.javaserver.user.model.User;

import javax.persistence.*;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Set;

@Entity
public class PassedTest implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne
    private User user;
    @OneToMany(mappedBy = "passed_test", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PassedQuestion> passedQuestions;

    private OffsetDateTime passedAt;
    private double rating;

    public PassedTest() {
    }


}
