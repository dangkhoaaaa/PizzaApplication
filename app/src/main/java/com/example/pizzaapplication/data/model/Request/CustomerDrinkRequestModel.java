package com.example.pizzaapplication.data.model.Request;

import com.google.gson.annotations.SerializedName;

public class CustomerDrinkRequestModel {

    @SerializedName("id")
    private int id;

    @SerializedName("orderId")
    private int orderId;

    @SerializedName("drinkId")
    private int drinkId;

    @SerializedName("price")
    private double price;

    // Default constructor
    public CustomerDrinkRequestModel() {
        this.id = 0;
        this.orderId = 0;
        this.drinkId = 0;
        this.price = 0;
    }

    // Parameterized constructor
    public CustomerDrinkRequestModel(int id, int orderId, int drinkId, double price) {
        this.id = id;
        this.orderId = orderId;
        this.drinkId = drinkId;
        this.price = price;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getDrinkId() {
        return drinkId;
    }

    public void setDrinkId(int drinkId) {
        this.drinkId = drinkId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // toString method to print CustomerDrink object details
    @Override
    public String toString() {
        return "CustomerDrink{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", drinkId=" + drinkId +
                ", price=" + price +
                '}';
    }
}
