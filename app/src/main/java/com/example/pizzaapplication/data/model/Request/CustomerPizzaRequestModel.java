package com.example.pizzaapplication.data.model.Request;

import com.example.pizzaapplication.data.model.CustomerOrder;
import com.example.pizzaapplication.data.model.Pizza;
import com.example.pizzaapplication.data.model.Size;
import com.example.pizzaapplication.data.model.Topping;
import com.google.gson.annotations.SerializedName;



public class CustomerPizzaRequestModel {

    @SerializedName("quantity")
    private int quantity;



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

        this.quantity = 0;
        this.pizzaId = 0;
        this.sizeId = 0;
        this.toppingId = 0;
        this.price = 0;

    }

    // Parameterized constructor
    public CustomerPizzaRequestModel(int quantity, int pizzaId, int sizeId, int toppingId, double price) {

        this.quantity = quantity;
        this.pizzaId = pizzaId;
        this.sizeId = sizeId;
        this.toppingId = toppingId;
        this.price = price;
    }

    // Getters and Setters
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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


                ", pizzaId=" + pizzaId +
                ", sizeId=" + sizeId +
                ", toppingId=" + toppingId +
                '}';
    }
}
