package com.example.eino.Models;

import java.util.LinkedList;

public class User {
private  final String ID;
private String email;
private String password;
private String displayName;
private ContactInfo contactInfo;
private int profilePicture; //TODO: EDIT LATER
private LinkedList<String> skills;
private boolean available;

    public User(String ID, String email, String password, String displayName, ContactInfo contactInfo, int profilePicture) {
        this.ID = ID;
        this.email = email;
        this.password = password;
        this.displayName = displayName;
        this.contactInfo = contactInfo;
        this.profilePicture = profilePicture;
    }

    public void addSkill(String skill){
        skills.add(skill);
    }

    //getters
    public String getID() {
        return ID;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getDisplayName() {
        return displayName;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public int getProfilePicture() {
        return profilePicture;
    }

    public LinkedList<String> getSkills() {
        return skills;
    }

    public boolean isAvailable() {
        return available;
    }

    //setters

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    public void setProfilePicture(int profilePicture) {
        this.profilePicture = profilePicture;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    private class ContactInfo {
        String type;
        String info;

        public ContactInfo(String type, String info) {
            this.type = type;
            this.info = info;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }
    }
}
