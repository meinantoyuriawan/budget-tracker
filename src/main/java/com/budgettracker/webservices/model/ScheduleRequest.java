package com.budgettracker.webservices.model;

public class ScheduleRequest {

    public String expensesId;
    public String start;
    public String end;
    public String byTime;

    public String getExpensesId() {
        return expensesId;
    }

    public void setExpensesId(String expensesId) {
        this.expensesId = expensesId;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getDDStart() {
        return start.substring(0,2);
    }
    // getMM
    public String getMMStart() {
        return start.substring(3,5);
    }
    // getYY
    public String getYYStart() {
        return start.substring(6);
    }

    public String getEnd() {
        return end;
    }

    public String getDDEnd() {
        return end.substring(0,2);
    }
    // getMM
    public String getMMEnd() {
        return end.substring(3,5);
    }
    // getYY
    public String getYYEnd() {
        return end.substring(6);
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getByTime() {
        return byTime;
    }

    public void setByTime(String byTime) {
        this.byTime = byTime;
    }
}
