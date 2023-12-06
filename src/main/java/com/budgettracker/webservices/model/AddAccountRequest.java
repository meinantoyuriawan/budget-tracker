package com.budgettracker.webservices.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AddAccountRequest {
    private String userId;
//    @NotBlank
    @NotBlank
    @Size(min = 2, max = 100)
    private String name;
//    @NotBlank
    @NotBlank
    @Size(min = 2, max = 100)
    private String type;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
