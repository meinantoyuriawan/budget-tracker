package com.budgettracker.webservices.Transaction;

import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
public class Expenses {

    private Integer id;

    private Integer user;
    private LocalDate expensesDate;
    private Integer accountId;
    private Integer amount;
    private String expensesType;
    private String description;

    public Expenses(Integer id, Integer user, LocalDate expensesDate, Integer accountId, Integer amount, String expensesType, String description) {
        this.id = id;
        this.user = user;
        this.expensesDate = expensesDate;
        this.accountId = accountId;
        this.amount = amount;
        this.expensesType = expensesType;
        this.description = description;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getExpensesDate() {
        return expensesDate;
    }

    public void setExpensesDate(LocalDate expensesDate) {
        this.expensesDate = expensesDate;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
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

    @Override
    public String toString() {
        return "Expenses{" +
                "id=" + id +
                ", expensesDate=" + expensesDate +
                ", accountId=" + accountId +
                ", amount=" + amount +
                ", expensesType='" + expensesType + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
