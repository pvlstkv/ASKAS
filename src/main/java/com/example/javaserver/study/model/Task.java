package com.example.javaserver.study.model;

import com.example.javaserver.common_data.model.SubjectSemester;
import com.example.javaserver.user.model.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unused")
@Entity
@Table(name = "tasks")
public class Task implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private TaskType type;

    @Column(length = 50)
    private String title;

    @Column(length = 1_000_000)
    private String description;

    @ManyToMany
    @JoinTable(
            name = "file_task",
            joinColumns = {@JoinColumn(name = "task_id")},
            inverseJoinColumns = {@JoinColumn(name = "file_id")})
    private Set<UserFile> userFiles;

    @ManyToMany
    @JoinTable(
            name = "task_semester",
            joinColumns = {@JoinColumn(name = "task_id")},
            inverseJoinColumns = {@JoinColumn(name = "semester_id")})
    private Set<SubjectSemester> semesters;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Work> works;

    @ManyToOne
    private User user;

    public Task() { }

    @PreRemove
    private void removeHandler() {
        userFiles.forEach(UserFile::decLinkCount);
        userFiles.clear();
        semesters.clear();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TaskType getType() {
        return type;
    }

    public void setType(TaskType type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<UserFile> getUserFiles() {
        return userFiles;
    }

    public void setUserFiles(Set<UserFile> userFiles) {
        this.userFiles = userFiles;
    }

    public Set<SubjectSemester> getSemesters() {
        return semesters;
    }

    public void setSemesters(Set<SubjectSemester> semesters) {
        this.semesters = semesters;
    }

    public List<Work> getWorks() {
        return works;
    }

    public void setWorks(List<Work> works) {
        this.works = works;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
