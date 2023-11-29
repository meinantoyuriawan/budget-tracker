package com.budgettracker.webservices.model;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;

@Entity
@Table(name = "users")
public class Users {
//    id VARCHAR(255) NOT NULL,
//    username VARCHAR(100) NOT NULL,
//    password VARCHAR(100) NOT NULL,
//    name VARCHAR(100) NOT NULL,
    @Id
    @UuidGenerator
    private String id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "user")
    private List<Accounts> accounts;

    @OneToMany(mappedBy = "user")
    private List<Goal> goal;
}
