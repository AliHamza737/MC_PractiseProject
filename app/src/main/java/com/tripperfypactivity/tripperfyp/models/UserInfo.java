package com.tripperfypactivity.tripperfyp.models;

public class UserInfo {
    public  String userFirstName,userLastName,userName,userCity,userPhoneno,userGender;

    public UserInfo() {

    }

    public UserInfo(String userFirstName, String userLastName, String userName, String userCity, String userPhoneno, String userGender) {
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userName = userName;
        this.userCity = userCity;
        this.userPhoneno = userPhoneno;
        this.userGender = userGender;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserCity() {
        return userCity;
    }

    public String getUserPhoneno() {
        return userPhoneno;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserCity(String userCity) {
        this.userCity = userCity;
    }

    public void setUserPhoneno(String userPhoneno) {
        this.userPhoneno = userPhoneno;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }
}
