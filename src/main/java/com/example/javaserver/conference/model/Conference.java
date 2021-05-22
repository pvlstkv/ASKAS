package com.example.javaserver.conference.model;

import com.example.javaserver.common_data.model.StudyGroup;
import com.example.javaserver.user.model.User;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Conference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "conference_group",
            joinColumns = {@JoinColumn(name = "conference_id")},
            inverseJoinColumns = {@JoinColumn(name = "group_id")})
    private Set<StudyGroup> studyGroups;

    @ManyToOne
    private User user;

    public Conference() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Set<StudyGroup> getStudyGroups() {
        return studyGroups;
    }

    public void setStudyGroups(Set<StudyGroup> studyGroups) {
        this.studyGroups = studyGroups;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
