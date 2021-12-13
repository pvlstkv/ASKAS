package com.example.javaserver.user.model;

import com.example.javaserver.common_data.model.Department;
import com.example.javaserver.common_data.model.StudyGroup;
import com.example.javaserver.common_data.model.Subject;
import com.example.javaserver.conference.model.Conference;
import com.example.javaserver.study.model.UserFile;
import com.example.javaserver.user.controller.dto.UserDto;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@SuppressWarnings("unused")
@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String login;

    private String password;

    private String email;

    private String firstName;

    private String lastName;

    private String patronymic;

    private String phone;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private UserFile avatar;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserContact> userContacts;

    @ManyToOne
    private StudyGroup studyGroup;

    @ManyToOne
    private Department department;

    @ManyToMany
    @JoinTable(
            name = "teacher_subject",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "subject_id")})
    private Set<Subject> teachingSubjects;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Conference> conferences;

    private UserRole role;


    //todo to add a gender!!!!!!

    public User() { }

    public User(String login, String password, StudyGroup studyGroup, UserRole role) {
        this.login = login;
        this.password = password;
        this.studyGroup = studyGroup;
        this.role = role;
    }

    public User(Integer id) {
        this.id = id;
    }

    public User(String login, String password, UserRole role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public User(String login, String password, String email, String firstName,
                String lastName, String patronymic, String phone, StudyGroup studyGroup, UserRole role) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.phone = phone;
        this.studyGroup = studyGroup;
        this.role = role;
    }

    public User(Integer id, String login, String password, String email, String firstName, String lastName,
                String patronymic, String phone, StudyGroup studyGroup, UserRole role) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.phone = phone;
        this.studyGroup = studyGroup;
        this.role = role;
    }

    @PreRemove
    private void removeHandler() {
        teachingSubjects.forEach(s -> s.getTeachers().remove(this));
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public UserFile getAvatar() {
        return avatar;
    }

    public void setAvatar(UserFile avatar) {
        if (this.avatar != null) {
            this.avatar.decLinkCount();
        }
        if (avatar != null) {
            avatar.incLinkCount();
        }
        this.avatar = avatar;
    }

    public Set<UserContact> getUserContacts() {
        return userContacts;
    }

    public void setUserContacts(Set<UserContact> userContacts) {
        this.userContacts = userContacts;
    }

    public StudyGroup getStudyGroup() {
        return studyGroup;
    }

    public void setStudyGroup(StudyGroup studyGroup) {
        this.studyGroup = studyGroup;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Set<Subject> getTeachingSubjects() {
        return teachingSubjects;
    }

    public void setTeachingSubjects(Set<Subject> teachingSubjects) {
        this.teachingSubjects = teachingSubjects;
    }

    public Set<Conference> getConferences() {
        return conferences;
    }

    public void setConferences(Set<Conference> conferences) {
        this.conferences = conferences;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}
