package com.example.javaserver.model;


import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "subjects")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    @ManyToOne(fetch = FetchType.LAZY)
    private User createdBy;

//    @OneToMany(mappedBy = "subject")
//    private List<Question> question;

    public Subject() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public List<Question> getQuestion() {
//        return question;
//    }
//
//    public void setQuestion(List<Question> question) {
//        this.question = question;
//    }

    public Subject(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
