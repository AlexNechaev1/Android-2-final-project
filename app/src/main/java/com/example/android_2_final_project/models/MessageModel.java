package com.example.android_2_final_project.models;

public class MessageModel {
    private String message;
    private String senderId;

    public MessageModel() {
    }

    public MessageModel(String message, String senderId) {
        this.message = message;
        this.senderId = senderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }
}
