package com.example.pizzaapplication.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Notification {

    @SerializedName("id")
    private int id;

    @SerializedName("user-id")
    private int userId;

    @SerializedName("content")
    private String content;

    @SerializedName("received-at")
    private String receivedAt;

    @SerializedName("status")
    private int status;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getReceivedAt() {
        return receivedAt;
    }

    public void setReceivedAt(String receivedAt) {
        this.receivedAt = receivedAt;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
