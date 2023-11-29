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

    public Users(String id, String username, String password, String name, List<Accounts> accounts, List<Goal> goal) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.accounts = accounts;
        this.goal = goal;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Accounts> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Accounts> accounts) {
        this.accounts = accounts;
    }

    public List<Goal> getGoal() {
        return goal;
    }

    public void setGoal(List<Goal> goal) {
        this.goal = goal;
    }
}
