package com.example.javaserver.user.model;

import javax.persistence.*;

@Entity
@Table(name = "user_contacts")
public class UserContact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private User user;

    @Enumerated(EnumType.STRING)
    private UserContactType type;

    @Column
    private String value;

    public UserContact() {
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

    public UserContactType getType() {
        return type;
    }

    public void setType(UserContactType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
