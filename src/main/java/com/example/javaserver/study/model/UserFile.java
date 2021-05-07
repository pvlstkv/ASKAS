package com.example.javaserver.study.model;

import com.example.javaserver.user.model.User;
import com.example.javaserver.user.model.UserRole;

import javax.persistence.*;
import java.io.Serializable;

@SuppressWarnings("unused")
@Entity
@Table(name = "files")
public class UserFile implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String name;

    private String contentType;

    private Long contentLength;

    private UserRole accessLevel;

    private Integer linkCount;

    @ManyToOne
    private User user;

    public UserFile() { }

    public void incLinkCount() {
        linkCount++;
    }

    public void decLinkCount() {
        if (linkCount <= 0) {
            throw new RuntimeException("Negative file link count");
        }
        linkCount--;
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

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Long getContentLength() {
        return contentLength;
    }

    public void setContentLength(Long contentLength) {
        this.contentLength = contentLength;
    }

    public UserRole getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(UserRole accessLevel) {
        this.accessLevel = accessLevel;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getLinkCount() {
        return linkCount;
    }

    public void setLinkCount(Integer linkCount) {
        this.linkCount = linkCount;
    }
}
