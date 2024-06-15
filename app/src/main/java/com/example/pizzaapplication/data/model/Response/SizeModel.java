package com.example.pizzaapplication.data.model.Response;

import com.example.pizzaapplication.data.model.CustomerPizza;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SizeModel implements Serializable {
    @SerializedName("sizeId")
    private int sizeId;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("price")
    private double price;



    // Default constructor
    public SizeModel() {
        this.sizeId = 0;
        this.name = "";
        this.description = "";
        this.price = 0.0;
    }

    // Parameterized constructor
    public SizeModel(int sizeId, String name, String description, double price) {
        this.sizeId = sizeId;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    // Getters and Setters
    public int getSizeId() {
        return sizeId;
    }

    public void setSizeId(int sizeId) {
        this.sizeId = sizeId;
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



    // toString method to print Size object details
    @Override
    public String toString() {
        return "Size{" +
                "sizeId=" + sizeId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }
}
