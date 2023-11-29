package com.budgettracker.webservices.model;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;

@Entity
@Table (name = "accounts")
public class Accounts {

    @Id
    @UuidGenerator
    private String id;

//    @Column(name = "user_id")
//    private String userId;

    @Column(name = "account_name")
    private String accountName;

    @Column(name = "account_type")
    private String accountType;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Users users;

    @OneToMany(mappedBy = "accounts")
    private List<Expenses> expenses;
}
