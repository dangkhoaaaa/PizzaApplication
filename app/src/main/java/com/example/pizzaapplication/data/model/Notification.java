package com.example.pizzaapplication.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Notification {

    @SerializedName("notificationId")
    private int notificationId;

    @SerializedName("userId")
    private int userId;

    @SerializedName("content")
    private String content;

    @SerializedName("receivedAt")
    private Date receivedAt;

    @SerializedName("user")
    private User user;

    // Default constructor
    public Notification() {
        this.notificationId = 0;
        this.userId = 0;
        this.content = "";
        this.receivedAt = new Date();
        this.user = new User(); // or initialize as null if needed
    }

    // Parameterized constructor
    public Notification(int notificationId, int userId, String content, Date receivedAt, User user) {
        this.notificationId = notificationId;
        this.userId = userId;
        this.content = content;
        this.receivedAt = receivedAt;
        this.user = user;
    }

    // Getters and Setters
    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getReceivedAt() {
        return receivedAt;
    }

    public void setReceivedAt(Date receivedAt) {
        this.receivedAt = receivedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // toString method to print Notification object details
    @Override
    public String toString() {
        return "Notification{" +
                "notificationId=" + notificationId +
                ", userId=" + userId +
                ", content='" + content + '\'' +
                ", receivedAt=" + receivedAt +
                ", user=" + user +
                '}';
    }
}
