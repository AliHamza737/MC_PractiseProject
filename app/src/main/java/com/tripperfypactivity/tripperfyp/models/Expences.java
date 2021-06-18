package com.tripperfypactivity.tripperfyp.models;

public class Expences {
    public String discription, memberName, type;

    public Expences() {

    }

    public Expences(String discription, String memberName, String type) {
        this.discription = discription;
        this.memberName = memberName;
        this.type = type;
    }

    public String getDiscription() {
        return discription;
    }

    public String getMemberName() {
        return memberName;
    }

    public String getType() {
        return type;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public void setType(String type) {
        this.type = type;
    }
}
