package com.example.eino.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.LinkedList;

public class User {
    @SerializedName("_id")
    @Expose
    private String ID;
    private String email;
    private String password;
    private String displayName;
    @SerializedName("contactInfoList")
    @Expose
    private ArrayList<ContactInfo> contactInfo = new ArrayList<>();
    @SerializedName("image")
    @Expose
    private String profilePicture; //TODO: EDIT LATER
    private LinkedList<String> skills = new LinkedList<>();
    private boolean available;

    public User(String ID, String email, String password, String displayName, ArrayList<ContactInfo> contactInfo, String profilePicture) {
        this.ID = ID;
        this.email = email;
        this.password = password;
        this.displayName = displayName;
        this.contactInfo = contactInfo;
        this.profilePicture = profilePicture;
    }

    public User(String ID, String email, String password, String name, String surname, ArrayList<ContactInfo> contactInfo, String profilePicture) {
        this(ID, email, password, name + " " + surname, contactInfo, profilePicture);
    }

    public User() {
        //to facilitate makeUser method in signUpActivity
    }

    public void addSkill(String skill) {
        skills.add(skill);
    }

    //getters
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getDisplayName() {
        return displayName;
    }

    public ArrayList<ContactInfo> getContactInfo() {
        return contactInfo;
    }

    public String getProfilePicture() {
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

    public void addContactInfo(String type, String info) {
        this.contactInfo.add(new ContactInfo(type, info));
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getID() {
        return ID;
    }

    public String getUsername() {
        return getEmail().substring(0, getEmail().indexOf('@'));
    }

    public class ContactInfo {
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
