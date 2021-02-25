package com.example.javaserver.model.user_file;

import com.example.javaserver.model.User;
import com.example.javaserver.model.common_data.Subject;
import com.example.javaserver.model.common_data.SubjectSemester;
import com.sun.istack.NotNull;
import javax.persistence.*;
import java.io.Serializable;

@SuppressWarnings("unused")
@Entity
@Table(name = "user_files")
public class UserFile implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, unique = true)
    //@NotBlank
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    @ManyToOne
    @JoinColumn(name = "subject_semester_id")
    //@NotNull
    private SubjectSemester subjectSemester;

    @Lob
    //@NotBlank
    private byte[] data;

    public UserFile() {}

    public UserFile(String name, User user, SubjectSemester subjectSemester, byte[] data) {
        this.name = name;
        this.user = user;
        this.subjectSemester = subjectSemester;
        this.data = data;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public SubjectSemester getSubjectSemester() {
        return subjectSemester;
    }

    public void setSubjectSemester(SubjectSemester subjectSemester) {
        this.subjectSemester = subjectSemester;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
