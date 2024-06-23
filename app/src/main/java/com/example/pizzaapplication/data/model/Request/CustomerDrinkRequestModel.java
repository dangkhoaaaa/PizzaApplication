package com.example.pizzaapplication.data.model.Request;

import com.google.gson.annotations.SerializedName;

public class CustomerDrinkRequestModel {

    @SerializedName("quantity")
    private int quantity;

    @SerializedName("drinkId")
    private int drinkId;

    @SerializedName("price")
    private double price;

    // Default constructor
    public CustomerDrinkRequestModel() {
        this.quantity = 0;

        this.drinkId = 0;
        this.price = 0;
    }

    // Parameterized constructor
    public CustomerDrinkRequestModel(int quantity, int drinkId, double price) {
        this.quantity = quantity;

        this.drinkId = drinkId;
        this.price = price;
    }

    // Getters and Setters
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int id) {
        this.quantity = quantity;
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

                ", drinkId=" + drinkId +
                ", price=" + price +
                '}';
    }
}
