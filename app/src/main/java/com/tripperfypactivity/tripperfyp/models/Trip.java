package com.tripperfypactivity.tripperfyp.models;

import java.util.HashMap;

public class Trip {
    public String tripName, startDate, endDate, startLocation, endLocation, tripExpences, id ,creatDate,tripadmin;
    HashMap<String, Object> timestampCreated;

    public Trip() {
    }

    public Trip(String tripName, String startDate, String endDate, String startLocation, String endLocation, String exPences, String id,String tripadmin) {
        this.tripName = tripName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.tripExpences = exPences;
        this.id = id;
        this.tripadmin=tripadmin;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    public String getTripExpences() {
        return tripExpences;
    }

    public void setTripExpences(String tripExpences) {
        this.tripExpences = tripExpences;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatDate() {
        return creatDate;
    }

    public void setCreatDate(String creatDate) {
        this.creatDate = creatDate;
    }

    public HashMap<String, Object> getTimestampCreated() {
        return timestampCreated;
    }

    public void setTimestampCreated(HashMap<String, Object> timestampCreated) {
        this.timestampCreated = timestampCreated;
    }

    public String getTripadmin() {
        return tripadmin;
    }

    public void setTripadmin(String tripadmin) {
        this.tripadmin = tripadmin;
    }
}

