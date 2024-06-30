package com.example.pizzaapplication.data.model.Response;

public class UsersModel {
    private int userId;
    private String name;
    private String email;
    private int role;
    private String verificationToken;
    private String verifiedAt;

    // Constructor, getter và setter methods
    public UsersModel(int userId, String name, String email, int role, String verificationToken, String verifiedAt) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.role = role;
        this.verificationToken = verificationToken;
        this.verifiedAt = verifiedAt;
    }
    //constructor
    public UsersModel() {
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getRole() {
        return role;
    }

    public String getVerificationToken() {
        return verificationToken;
    }

    public String getVerifiedAt() {
        return verifiedAt;
    }

    public void setVerifiedAt(String verifiedAt) {
        this.verifiedAt = verifiedAt;
    }
}
