package com.lightema.xinydays.modules.users.entities;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.lightema.xinydays.core.data.entities.Auditable;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import com.lightema.xinydays.modules.projects.entities.Project;

import java.util.List;

@Getter
@Setter
@Entity(name = "users")
@Table(name = "users")
@JsonPropertyOrder({"id", "firstName", "lastName", "phone", "email", "password"})
public class User extends Auditable {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 15)
    private String firstName;

    @Column(nullable = false, length = 15)
    private String lastName;

    @Column(nullable = false, length = 12)
    private String phone;

    @Column(nullable = false, length = 30)
    private String email;

    @Column(nullable = false)
    private String password;
}
