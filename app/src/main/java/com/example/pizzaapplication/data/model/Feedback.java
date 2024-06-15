package com.example.pizzaapplication.data.model;

import com.google.gson.annotations.SerializedName;

public class Feedback {

    @SerializedName("feedbackId")
    private int feedbackId;

    @SerializedName("userId")
    private int userId;

    @SerializedName("rating")
    private int rating;

    @SerializedName("comment")
    private String comment;

    @SerializedName("user")
    private User user;

    // Default constructor
    public Feedback() {
        this.feedbackId = 0;
        this.userId = 0;
        this.rating = 0;
        this.comment = "";
        this.user = new User(); // or initialize as null if needed
    }

    // Parameterized constructor
    public Feedback(int feedbackId, int userId, int rating, String comment, User user) {
        this.feedbackId = feedbackId;
        this.userId = userId;
        this.rating = rating;
        this.comment = comment;
        this.user = user;
    }

    // Getters and Setters
    public int getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(int feedbackId) {
        this.feedbackId = feedbackId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // toString method to print Feedback object details
    @Override
    public String toString() {
        return "Feedback{" +
                "feedbackId=" + feedbackId +
                ", userId=" + userId +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", user=" + user +
                '}';
    }
}
