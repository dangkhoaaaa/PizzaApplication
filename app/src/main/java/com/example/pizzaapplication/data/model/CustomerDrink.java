package com.example.pizzaapplication.data.model;

import com.google.gson.annotations.SerializedName;

public class CustomerDrink {

    @SerializedName("id")
    private int id;

    @SerializedName("drinkId")
    private int drinkId;

    @SerializedName("orderId")
    private int orderId;

    @SerializedName("drink")
    private Drink drink;

    @SerializedName("customerOrder")
    private CustomerOrder customerOrder;

    // Default constructor
    public CustomerDrink() {
        this.id = 0;
        this.drinkId = 0;
        this.orderId = 0;
        this.drink = null; // or initialize as new Drink();
        this.customerOrder = null; // or initialize as new CustomerOrder();
    }

    // Parameterized constructor
    public CustomerDrink(int id, int drinkId, int orderId, Drink drink, CustomerOrder customerOrder) {
        this.id = id;
        this.drinkId = drinkId;
        this.orderId = orderId;
        this.drink = drink;
        this.customerOrder = customerOrder;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDrinkId() {
        return drinkId;
    }

    public void setDrinkId(int drinkId) {
        this.drinkId = drinkId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Drink getDrink() {
        return drink;
    }

    public void setDrink(Drink drink) {
        this.drink = drink;
    }

    public CustomerOrder getCustomerOrder() {
        return customerOrder;
    }

    public void setCustomerOrder(CustomerOrder customerOrder) {
        this.customerOrder = customerOrder;
    }

    // toString method to print CustomerDrink object details
    @Override
    public String toString() {
        return "CustomerDrink{" +
                "id=" + id +
                ", drinkId=" + drinkId +
                ", orderId=" + orderId +
                ", drink=" + drink +
                ", customerOrder=" + customerOrder +
                '}';
    }
}
