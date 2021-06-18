package com.tripperfypactivity.tripperfyp.models;

public class User {
    public  String firstName,lastName,email,userName,password,id,userPhonNo,triplist;

    public User(){

    }

    public User(String fName, String lName, String email, String uName, String password, String id, String infochack,String userPhoneno, String triplist) {
        this.firstName = fName;
        this.lastName = lName;
        this.email = email;
        this.userName= uName;
        this.password = password;
        this.id = id;
        this.triplist=triplist;
        this.userPhonNo=userPhoneno;


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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTripid() {
        return triplist;
    }

    public void setTripid(String tripid) {
        this.triplist = tripid;
    }

    public String getUserPhoneno() {
        return userPhonNo;
    }

    public void setUserPhoneno(String userPhoneno) {
        this.userPhonNo = userPhoneno;
    }


}
