package com.example.javaserver.testing.n.model.saving_result;

import com.example.javaserver.testing.model.Theme;
import com.example.javaserver.testing.n.model.saving_result.question.PassedQuestionData;
import com.example.javaserver.user.model.User;
import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;

@Entity
public class PassedTest implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "passedTest", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PassedQuestionData> passedQuestions;

    @ManyToOne
    @JsonProperty("themeId")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private Theme theme;
    private OffsetDateTime passedAt;
    private Integer ratingInPercent;

    public PassedTest() {
    }

    public PassedTest(Long id, User user, List<PassedQuestionData> passedQuestions, Theme theme, OffsetDateTime passedAt, Integer ratingInPercent) {
        this.id = id;
        this.user = user;
        this.passedQuestions = passedQuestions;
        this.theme = theme;
        this.passedAt = passedAt;
        this.ratingInPercent = ratingInPercent;
    }

    public PassedTest(User user, List<PassedQuestionData> passedQuestions, Theme theme, OffsetDateTime passedAt, Integer ratingInPercent) {
        this.user = user;
        this.passedQuestions = passedQuestions;
        this.theme = theme;
        this.passedAt = passedAt;
        this.ratingInPercent = ratingInPercent;
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

    public List<PassedQuestionData> getPassedQuestions() {
        return passedQuestions;
    }

    public void setPassedQuestions(List<PassedQuestionData> passedQuestions) {
        this.passedQuestions = passedQuestions;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public OffsetDateTime getPassedAt() {
        return passedAt;
    }

    public void setPassedAt(OffsetDateTime passedAt) {
        this.passedAt = passedAt;
    }

    public Integer getRatingInPercent() {
        return ratingInPercent;
    }

    public void setRatingInPercent(Integer ratingInPercent) {
        this.ratingInPercent = ratingInPercent;
    }
}
