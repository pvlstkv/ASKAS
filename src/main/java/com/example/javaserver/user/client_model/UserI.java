package com.example.javaserver.user.client_model;

import com.example.javaserver.user.model.User;
import com.example.javaserver.user.model.UserRole;

import javax.persistence.ManyToOne;

public class UserI {
    private Integer id;
    private String login;

    private String password;

    private String email;

    private String firstName;

    private String lastName;

    private String patronymic;

    private String phone;

    private String studyGroupName;

    private String departmentName;

    private UserRole role;

    public UserI() {
    }

    public UserI(User user) {
        this.id = user.getId();
        this.login = user.getLogin();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.patronymic = user.getPatronymic();
        this.phone = user.getPhone();
        this.studyGroupName = user.getFirstName();
        this.role = user.getRole();

    }

    public UserI(String login, String password, String email, String firstName, String lastName, String patronymic,
                 String phone, String studyGroupName, String departmentName, UserRole role) {

        this.login = login;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.phone = phone;
        this.studyGroupName = studyGroupName;
        this.departmentName = departmentName;
        this.role = role;
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

    public String getStudyGroupName() {
        return studyGroupName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setStudyGroupName(String studyGroupName) {
        this.studyGroupName = studyGroupName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
