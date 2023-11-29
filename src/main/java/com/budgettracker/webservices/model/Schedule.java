package com.budgettracker.webservices.model;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "schedule")
public class Schedule {
    @Id
    @UuidGenerator
    private String id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "start_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    @Column(name = "end_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    @Column(name = "by_time")
    private String byTime;

    @ManyToOne
    @JoinColumn(name = "expenses_id", referencedColumnName = "id")
    private Accounts accounts;
}
