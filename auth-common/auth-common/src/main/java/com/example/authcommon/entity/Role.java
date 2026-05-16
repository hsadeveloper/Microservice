package com.example.authcommon.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String name; // e.g., ROLE_ADMIN, ROLE_USER

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private Set<UserDetails> users = new HashSet<>();

    // Getters and Setters
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

    public Set<UserDetails> getUsers() {
        return users;
    }

    public void setUsers(Set<UserDetails> users) {
        this.users = users;
    }
}