package com.budgettracker.webservices.model;

public class UserGoalResponse {
    private String daily;
    private String weekly;
    private String monthly;
    private String yearly;

    public String getDaily() {
        return daily;
    }

    public void setDaily(String daily) {
        this.daily = daily;
    }

    public String getWeekly() {
        return weekly;
    }

    public void setWeekly(String weekly) {
        this.weekly = weekly;
    }

    public String getMonthly() {
        return monthly;
    }

    public void setMonthly(String monthly) {
        this.monthly = monthly;
    }

    public String getYearly() {
        return yearly;
    }

    public void setYearly(String yearly) {
        this.yearly = yearly;
    }
}
