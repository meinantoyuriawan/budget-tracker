package com.budgettracker.webservices.model;

import java.util.Date;

public class AddExpsensesRequest {

    private String date;
    private String account;
    private Long amount;
    private String type;
    private String description;
    private String userId;

    public String getDate() {
        return date;
    }

    // getDD
    public String getDD() {
        return date.substring(0,2);
    }
    // getMM
    public String getMM() {
        return date.substring(3,5);
    }
    // getYY
    public String getYY() {
        return date.substring(6);
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
