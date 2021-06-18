package com.tripperfypactivity.tripperfyp.models;

public class UserTripList{
    public String startDate,endDate,id,tripadmin;

    public UserTripList() {
    }

    public UserTripList(String startDate, String endDate, String id,String tripadmin) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.id = id;
        this.tripadmin=tripadmin;
    }

    public String getTripadmin() {
        return tripadmin;
    }

    public void setTripadmin(String tripadmin) {
        this.tripadmin = tripadmin;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getId() {
        return id;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setId(String id) {
        this.id = id;
    }
}

