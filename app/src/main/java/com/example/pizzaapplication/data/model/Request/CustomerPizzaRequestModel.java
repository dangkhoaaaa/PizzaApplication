package com.example.pizzaapplication.data.model.Request;

import com.example.pizzaapplication.data.model.CustomerOrder;
import com.example.pizzaapplication.data.model.Pizza;
import com.example.pizzaapplication.data.model.Size;
import com.example.pizzaapplication.data.model.Topping;
import com.google.gson.annotations.SerializedName;



public class CustomerPizzaRequestModel {

    @SerializedName("id")
    private int id;

    @SerializedName("orderId")
    private int orderId;

    @SerializedName("pizzaId")
    private int pizzaId;

    @SerializedName("sizeId")
    private int sizeId;

    @SerializedName("toppingId")
    private int toppingId;

    @SerializedName("price")
    private double price;

    // Default constructor
    public CustomerPizzaRequestModel() {
        this.id = 0;
        this.orderId = 0;
        this.pizzaId = 0;
        this.sizeId = 0;
        this.toppingId = 0;
        this.price = 0;

    }

    // Parameterized constructor
    public CustomerPizzaRequestModel(int id, int orderId, int pizzaId, int sizeId, int toppingId, double price) {
        this.id = id;
        this.orderId = orderId;
        this.pizzaId = pizzaId;
        this.sizeId = sizeId;
        this.toppingId = toppingId;
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

    public int getPizzaId() {
        return pizzaId;
    }

    public void setPizzaId(int pizzaId) {
        this.pizzaId = pizzaId;
    }

    public int getSizeId() {
        return sizeId;
    }

    public void setSizeId(int sizeId) {
        this.sizeId = sizeId;
    }

    public int getToppingId() {
        return toppingId;
    }

    public void setToppingId(int toppingId) {
        this.toppingId = toppingId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }



    // toString method to print CustomerPizza object details
    @Override
    public String toString() {
        return "CustomerPizza{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", pizzaId=" + pizzaId +
                ", sizeId=" + sizeId +
                ", toppingId=" + toppingId +
                '}';
    }
}
