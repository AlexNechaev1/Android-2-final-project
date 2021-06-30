package com.example.android_2_final_project.models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@IgnoreExtraProperties
public class UserModel implements Serializable {

    private String email;
    private String username;
    private List<String> answers;
    private String bio;
    private String profileImage;
    private Map<String, String> chats;

    public UserModel() {
    }

    public UserModel(String email,
                     String username,
                     List<String> answers,
                     String bio,
                     String profileImage,
                     Map<String, String> chats) {
        this.email = email;
        this.username = username;
        this.answers = answers;
        this.bio = bio;
        this.profileImage = profileImage;
        this.chats = chats;
    }

    public Map<String, String> getChats() {
        return chats;
    }

    public void setChats(Map<String, String> chats) {
        this.chats = chats;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
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

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
