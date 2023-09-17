package com.lightema.xinydays.modules.users.entities;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.lightema.xinydays.core.data.entities.Auditable;
import com.lightema.xinydays.modules.users.models.Role;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@Setter
@Entity(name = "users")
@Table(name = "users")
@JsonPropertyOrder({"id", "firstName", "lastName", "phone", "email", "password"})
public class User extends Auditable implements UserDetails {
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

    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
