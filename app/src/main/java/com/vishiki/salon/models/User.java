package com.vishiki.salon.models;

public class User {
    private String name;
    private String phoneNumber;
    private String dob;
    private String username;
    private String password;
    private String imageUrl;

    public User(String name, String phoneNumber, String dob, String username, String password) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.dob = dob;
        this.username = username;
        this.password = password;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public User(String name, String phoneNumber, String dob, String username, String password, String imageUrl) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.dob = dob;
        this.username = username;
        this.password = password;
        this.imageUrl = imageUrl;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
