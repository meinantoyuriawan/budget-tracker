package com.budgettracker.webservices.model;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "goal")
public class Goal {
    @Id
    @UuidGenerator
    private String id;

    @Column(name = "by_time")
    private String byTime;

    @Column(name = "by_account")
    private String byAccount;

    @Column(name = "amount")
    private Long amount;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Users users;
}
