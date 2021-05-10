package com.example.javaserver.user.controller.dto;

import com.example.javaserver.user.model.User;
import com.example.javaserver.user.model.UserRole;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserDto {
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @NotNull
    @NotEmpty
    @Size(min = 1, max = 50)
    private Integer id;

    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @NotNull
    @NotEmpty
    @Size(min = 1, max = 50)
    private String login;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @NotEmpty
    @Size(min = 1, max = 50)
    private String password;

    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @NotNull
    @NotEmpty
    @Size(min = 1, max = 50)
    private String email;

    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @NotNull
    @NotEmpty
    @Size(min = 1, max = 50)
    private String firstName;

    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @NotNull
    @NotEmpty
    @Size(min = 1, max = 50)
    private String lastName;

    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @NotNull
    @NotEmpty
    @Size(min = 1, max = 50)
    private String patronymic;

    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @NotNull
    @NotEmpty
    @Size(min = 1, max = 50)
    private String phone;

    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @NotNull
    @NotEmpty
    @Size(min = 1, max = 50)
    private Long studyGroupId;

    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @NotNull
    @NotEmpty
    @Size(min = 1, max = 50)
    private Long departmentId;

    @NotNull
    @NotEmpty
    private UserRole role;

    public UserDto() {
    }

    public Long getStudyGroupId() {
        return studyGroupId;
    }

    public void setStudyGroupId(Long studyGroupId) {
        this.studyGroupId = studyGroupId;
    }

    public UserDto(User user) {
        this.id = user.getId();
        this.login = user.getLogin();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.patronymic = user.getPatronymic();
        this.phone = user.getPhone();
        this.role = user.getRole();

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

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
