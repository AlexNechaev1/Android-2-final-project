package com.example.android_2_final_project.models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.ArrayList;

@IgnoreExtraProperties
public class User implements Serializable {

    private java.lang.String email;
    private java.lang.String username;
    private ArrayList<String> answers;

    public User() {
    }

    public User(String email, String username, ArrayList<String> answers) {
        this.email = email;
        this.username = username;
        this.answers = answers;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }
}
