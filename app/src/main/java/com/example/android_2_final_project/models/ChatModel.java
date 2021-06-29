package com.example.android_2_final_project.models;


public class ChatModel {

    private String contactID;
    private UserModel contact;

    public ChatModel() {
    }

    public ChatModel(String chatID, UserModel contact) {
        this.contactID = chatID;
        this.contact = contact;
    }

    public String getContactID() {
        return contactID;
    }

    public void setContactID(String contactID) {
        this.contactID = contactID;
    }

    public UserModel getContact() {
        return contact;
    }

    public void setContact(UserModel contact) {
        this.contact = contact;
    }
}
