package com.example.pizzaapplication.data.model;

import com.example.pizzaapplication.utils.UserRole;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Account {

    @SerializedName("userId")
    private int userId;

    @SerializedName("firstName")
    private String firstName;

    @SerializedName("lastName")
    private String lastName;

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("role")
    private UserRole role;

    @SerializedName("profilePic")
    private String profilePic;

    @SerializedName("notifications")
    private List<Notification> notifications;

    @SerializedName("messages")
    private List<Message> messages;

    @SerializedName("customerOrders")
    private List<CustomerOrder> customerOrders;

    // Default constructor
    public Account() {
        this.userId = 0;
        this.firstName = "";
        this.lastName = "";
        this.email = "";
        this.password = "";
        this.role = UserRole.GUEST;
        this.profilePic = "";
        this.notifications = null;
        this.messages = null;
        this.customerOrders = null;
    }

    // Parameterized constructor
    public Account(int userId, String firstName, String lastName, String email, String password, UserRole role, String profilePic, List<Notification> notifications, List<Message> messages, List<CustomerOrder> customerOrders) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.profilePic = profilePic;
        this.notifications = notifications;
        this.messages = messages;
        this.customerOrders = customerOrders;
    }

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public List<CustomerOrder> getCustomerOrders() {
        return customerOrders;
    }

    public void setCustomerOrders(List<CustomerOrder> customerOrders) {
        this.customerOrders = customerOrders;
    }

    // toString method to print User object details
    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", profilePic='" + profilePic + '\'' +
                ", notifications=" + notifications +
                ", messages=" + messages +
                ", customerOrders=" + customerOrders +
                '}';
    }
}
