package com.example.javaserver.action.model;

import com.example.javaserver.user.model.User;
import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "action")
@Data
public class Action {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "action_date")
    private LocalDate actionDate;

    @ManyToOne
    @JoinColumn(name="action_type_id", nullable=false)
    private ActionType actionType;

    @ManyToMany
    @JoinTable(
        name = "users_action",
        joinColumns = {@JoinColumn(name = "action_id")},
        inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private Set<User> users = new HashSet<>();


}
