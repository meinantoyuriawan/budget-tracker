package com.budgettracker.webservices.model;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "expenses")
public class Expenses {

//    id VARCHAR(255) NOT NULL,
//    user_id VARCHAR(255) NOT NULL,
//    expenses_date DATE NOT NULL,
//    account_id VARCHAR(255) NOT NULL,
//    amount BIGINT NOT NULL,
//    type VARCHAR(50) NOT NULL,
//    description VARCHAR(200) NOT NULL,
//    PRIMARY KEY (id),
//    FOREIGN KEY (account_id) REFERENCES accounts(id)

    @Id
//    @UuidGenerator
    private String id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "expenses_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @Column(name = "amount")
    private Long amount;

    @Column(name = "type")
    private String expensesType;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Accounts accounts;

    @OneToMany(mappedBy = "expenses")
    private List<Schedule> schedule;

    public Expenses(){

    }
//    public Expenses(String id, String userId, Date date, Long amount, String expensesType, String description, Accounts accounts, List<Schedule> schedule) {
//        this.id = id;
//        this.userId = userId;
//        this.date = date;
//        this.amount = amount;
//        this.expensesType = expensesType;
//        this.description = description;
//        this.accounts = accounts;
//        this.schedule = schedule;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getExpensesType() {
        return expensesType;
    }

    public void setExpensesType(String expensesType) {
        this.expensesType = expensesType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Accounts getAccounts() {
        return accounts;
    }

    public void setAccounts(Accounts accounts) {
        this.accounts = accounts;
    }

    public List<Schedule> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<Schedule> schedule) {
        this.schedule = schedule;
    }
}
