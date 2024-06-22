package com.example.pizzaapplication.data.model;

public class Profile {
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String address;
    private String email;
    private String phone;
    private String profilePic;

    // default constructor
    public Profile() {
        this.firstName = "";
        this.lastName = "";
        this.dateOfBirth = "";
        this.address = "";
        this.email = "";
        this.phone = "";
        this.profilePic = "";
    }

    // Parameterized constructor

    public Profile(String firstName, String lastName, String dateOfBirth, String address, String email, String phone, String profilePic) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.profilePic = profilePic;
    }

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
        return "Profile{" +
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
