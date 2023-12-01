package com.budgettracker.webservices.model;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "goal")
public class Goal {
    @Id
//    @UuidGenerator
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

    public Goal(){

    }
//    public Goal(String id, String byTime, String byAccount, Long amount, Users users) {
//        this.id = id;
//        this.byTime = byTime;
//        this.byAccount = byAccount;
//        this.amount = amount;
//        this.users = users;
//    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getByTime() {
        return byTime;
    }

    public void setByTime(String byTime) {
        this.byTime = byTime;
    }

    public String getByAccount() {
        return byAccount;
    }

    public void setByAccount(String byAccount) {
        this.byAccount = byAccount;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }
}
