package com.example.javaserver.testing.models.saving_results;

import com.example.javaserver.user.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Set;

@Entity
@Table(name = "passed_tests")
public class PassedTest implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JsonIgnore
    private User user;
    @OneToMany(mappedBy = "passedTest", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PassedQuestion> passedQuestions;

    private OffsetDateTime passedAt;
    private Integer ratingInPercent;

    public PassedTest() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<PassedQuestion> getPassedQuestions() {
        return passedQuestions;
    }

    public void setPassedQuestions(Set<PassedQuestion> passedQuestions) {
        this.passedQuestions = passedQuestions;
    }

    public OffsetDateTime getPassedAt() {
        return passedAt;
    }

    public void setPassedAt(OffsetDateTime passedAt) {
        this.passedAt = passedAt;
    }

    public Integer getRating() {
        return ratingInPercent;
    }

    public void setRating(Integer ratingInPercent) {
        this.ratingInPercent = ratingInPercent;
    }
}
