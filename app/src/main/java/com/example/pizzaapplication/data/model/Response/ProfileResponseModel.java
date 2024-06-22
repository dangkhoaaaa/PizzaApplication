package com.example.pizzaapplication.data.model.Response;

import com.google.gson.annotations.SerializedName;

public class ProfileResponseModel {
    @SerializedName("first-name")
    private String firstName;

    @SerializedName("last-name")
    private String lastName;

    @SerializedName("date-of-birth")
    private String dateOfBirth;

    @SerializedName("address")
    private String address;

    @SerializedName("email")
    private String email;

    @SerializedName("phone")
    private String phone;

    @SerializedName("profile-pic")
    private String profilePic;

    //default constructor
    public ProfileResponseModel() {
        this.email = "";
        this.firstName = "";
        this.lastName = "";
        this.dateOfBirth = "";
        this.address = "";
        this.phone = "";
        this.profilePic = "";
    }

    // Parameterized constructor
    public ProfileResponseModel(String email, String firstName, String lastName, String dateOfBirth, String address, String phone, String profilePic) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.phone = phone;
        this.profilePic = profilePic;
    }

    // Getters and setters

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    @Override
    public String toString() {
        return "ProfileResponseModel{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", profilePic='" + profilePic + '\'' +
                '}';
    }
}
