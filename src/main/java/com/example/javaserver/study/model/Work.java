package com.example.javaserver.study.model;

import com.example.javaserver.common_data.model.Mark;
import com.example.javaserver.user.model.User;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "works")
public class Work {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("taskId")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne
    private Task task;

    @JsonProperty("userId")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne
    private User user;

    @JsonProperty("fileIds")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToMany
    @JoinTable(
            name = "file_work",
            joinColumns = {@JoinColumn(name = "work_id")},
            inverseJoinColumns = {@JoinColumn(name = "file_id")})
    private Set<UserFile> userFiles;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    @Column(length = 1_000_000)
    private String teacherComment;

    @Column(length = 1_000_000)
    private String studentComment;

    private Mark mark;

    public Work() {
    }

    @PreRemove
    private void removeHandler() {
        userFiles.forEach(UserFile::decLinkCount);
        userFiles.clear();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<UserFile> getUserFiles() {
        return userFiles;
    }

    public void setUserFiles(Set<UserFile> userFiles) {
        this.userFiles = userFiles;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getTeacherComment() {
        return teacherComment;
    }

    public void setTeacherComment(String teacherComment) {
        this.teacherComment = teacherComment;
    }

    public String getStudentComment() {
        return studentComment;
    }

    public void setStudentComment(String studentComment) {
        this.studentComment = studentComment;
    }

    public Mark getMark() {
        return mark;
    }

    public void setMark(Mark mark) {
        this.mark = mark;
    }
}

