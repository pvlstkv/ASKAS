package com.example.javaserver.study.model;

import com.example.javaserver.common_data.model.SubjectSemester;
import com.example.javaserver.user.model.User;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@SuppressWarnings("unused")
@Entity
@Table(name = "literature")
public class Literature implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LiteratureType type;
    private String title;
    private String authors;
    private String description;

    @JsonProperty("fileIds")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToMany
    @JoinTable(
            name = "file_literature",
            joinColumns = {@JoinColumn(name = "literature_id")},
            inverseJoinColumns = {@JoinColumn(name = "file_id")})
    private Set<UserFile> files;

    @JsonProperty("userId")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne
    private User user;

    @JsonProperty("semesterIds")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToMany
    @JoinTable(
            name = "literature_semester",
            joinColumns = {@JoinColumn(name = "literature_id")},
            inverseJoinColumns = {@JoinColumn(name = "semester_id")})
    private Set<SubjectSemester> semesters;

    public Literature() {
    }

    @PreRemove
    private void removeHandler() {
        files.forEach(UserFile::decLinkCount);
        files.clear();
        semesters.clear();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LiteratureType getType() {
        return type;
    }

    public void setType(LiteratureType type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<UserFile> getFiles() {
        return files;
    }

    public void setFiles(Set<UserFile> files) {
        this.files = files;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<SubjectSemester> getSemesters() {
        return semesters;
    }

    public void setSemesters(Set<SubjectSemester> semesters) {
        this.semesters = semesters;
    }
}
