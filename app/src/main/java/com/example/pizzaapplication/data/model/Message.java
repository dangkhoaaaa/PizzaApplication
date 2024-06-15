package com.example.pizzaapplication.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Message {

    @SerializedName("messageId")
    private int messageId;

    @SerializedName("senderId")
    private int senderId;

    @SerializedName("receiverId")
    private int receiverId;

    @SerializedName("receiver")
    private User receiver;

    @SerializedName("content")
    private String content;

    @SerializedName("sentAt")
    private Date sentAt;

    // Default constructor
    public Message() {
        this.messageId = 0;
        this.senderId = 0;
        this.receiverId = 0;
        this.receiver = new User(); // or initialize as null if needed
        this.content = "";
        this.sentAt = new Date();
    }

    // Parameterized constructor
    public Message(int messageId, int senderId, int receiverId, User receiver, String content, Date sentAt) {
        this.messageId = messageId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.receiver = receiver;
        this.content = content;
        this.sentAt = sentAt;
    }

    // Getters and Setters
    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getSentAt() {
        return sentAt;
    }

    public void setSentAt(Date sentAt) {
        this.sentAt = sentAt;
    }

    // toString method to print Message object details
    @Override
    public String toString() {
        return "Message{" +
                "messageId=" + messageId +
                ", senderId=" + senderId +
                ", receiverId=" + receiverId +
                ", receiver=" + receiver +
                ", content='" + content + '\'' +
                ", sentAt=" + sentAt +
                '}';
    }
}
