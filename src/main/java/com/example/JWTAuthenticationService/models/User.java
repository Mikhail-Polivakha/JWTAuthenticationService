package com.example.JWTAuthenticationService.models;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
public class User extends BaseEntity {

    //constructor for testing
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
    joinColumns = @JoinColumn(name = "users_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "roles_id", referencedColumnName = "id"))
    private List<Role> roles;

    @Column(name = "enabled")
    private boolean enabled;

    public User() {
    }
}
