package com.tripperfypactivity.tripperfyp.models;

public class TripMemberList {
    public String id;
    public  String firstName,lastName;

    public TripMemberList() {
    }

    public TripMemberList(String fName, String lName,String id) {
        this.firstName = fName;
        this.lastName = lName;
        this.id = id;
    }
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
