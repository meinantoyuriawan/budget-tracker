package com.budgettracker.webservices.model;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "schedule")
public class Schedule {
    @Id
//    @UuidGenerator
    private String id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "start_date")
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @Column(name = "end_date")
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @Column(name = "by_time")
    private String byTime;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "expenses_id", referencedColumnName = "id")
    private Expenses expenses;

    public Schedule(){

    }
//    public Schedule(String id, String userId, Date startDate, Date endDate, String byTime, Accounts accounts) {
//        this.id = id;
//        this.userId = userId;
//        this.startDate = startDate;
//        this.endDate = endDate;
//        this.byTime = byTime;
//        this.accounts = accounts;
//    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getByTime() {
        return byTime;
    }

    public void setByTime(String byTime) {
        this.byTime = byTime;
    }

    public Expenses getExpenses() {
        return expenses;
    }

    public void setExpenses(Expenses expenses) {
        this.expenses = expenses;
    }
}
