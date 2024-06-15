package com.example.pizzaapplication.data.model.Response;

import com.example.pizzaapplication.data.model.CustomerPizza;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ToppingModel implements Serializable {
    @SerializedName("toppingId")
    private int toppingId;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("price")
    private double price;


    // Default constructor
    public ToppingModel() {
        this.toppingId = 0;
        this.name = "";
        this.description = "";
        this.price = 0.0;
    }

    // Parameterized constructor
    public ToppingModel(int toppingId, String name, String description, double price) {
        this.toppingId = toppingId;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    // Getters and Setters
    public int getToppingId() {
        return toppingId;
    }

    public void setToppingId(int toppingId) {
        this.toppingId = toppingId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }



    // toString method to print Topping object details
    @Override
    public String toString() {
        return "Topping{" +
                "toppingId=" + toppingId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +

                '}';
    }
}
