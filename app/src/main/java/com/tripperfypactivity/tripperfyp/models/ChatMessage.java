package com.tripperfypactivity.tripperfyp.models;

import java.util.Date;

public class ChatMessage {
    private String messageText;
    private String messageUserName;
    private String messageUserId;
    private long messageTime;

    public ChatMessage(String messageText, String messageUser, String messageUserId) {
        this.messageText = messageText;
        this.messageUserName = messageUser;
        messageTime = new Date().getTime();
        this.messageUserId = messageUserId;
    }

    public ChatMessage(){

    }

    public String getMessageUserId() {
        return messageUserId;
    }

    public void setMessageUserId(String messageUserId) {
        this.messageUserId = messageUserId;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageUser() {
        return messageUserName;
    }

    public void setMessageUser(String messageUser) {
        this.messageUserName = messageUser;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }
}
