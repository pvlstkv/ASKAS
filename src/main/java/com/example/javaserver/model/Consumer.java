package com.example.javaserver.model;

import com.example.javaserver.configuration.Role;
import com.example.javaserver.model.commonData.Department;
import com.example.javaserver.model.commonData.StudyGroup;


import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
public class Consumer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String login;
    private String email;
    private String phoneNumber;
    private String password;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String avatar;
    private Role role;
    private int numberOfSemester;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private StudyGroup studyGroup;


    //todo add a language of interface

    public Consumer(long id, String login, String email, String phoneNumber, String password, String firstName,
                    String lastName, String patronymic, String avatar, Role role, int numberOfSemester,
                    OffsetDateTime createdAt, OffsetDateTime updatedAt, StudyGroup studyGroup) {
        this.id = id;
        this.login = login;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.avatar = avatar;
        this.role = role;
        this.numberOfSemester = numberOfSemester;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.studyGroup = studyGroup;
    }

    public Consumer(String firstName){
        this.firstName = firstName;
    }
    public Consumer() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public int getNumberOfSemester() {
        return numberOfSemester;
    }

    public void setNumberOfSemester(int numberOfSemester) {
        this.numberOfSemester = numberOfSemester;
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

    public StudyGroup getStudyGroup() {
        return studyGroup;
    }

    public void setStudyGroup(StudyGroup studyGroup) {
        this.studyGroup = studyGroup;
    }

}
