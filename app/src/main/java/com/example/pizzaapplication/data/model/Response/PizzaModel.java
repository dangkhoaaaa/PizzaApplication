package com.example.pizzaapplication.data.model.Response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class PizzaModel implements Serializable {

    @SerializedName("id")
    private int pizzaId;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("price")
    private double price;

    @SerializedName("image")
    private String image;

    // Default constructor
    public PizzaModel() {
        this.pizzaId = 0;
        this.name = "";
        this.description = "";
        this.price = 0.0;
        this.image = "";
    }

    // Parameterized constructor
    public PizzaModel(int pizzaId, String name, String description, double price, String image) {
        this.pizzaId = pizzaId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
    }

    // Getters and Setters
    public int getPizzaId() {
        return pizzaId;
    }

    public void setPizzaId(int pizzaId) {
        this.pizzaId = pizzaId;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    // toString method to print PizzaModel object details
    @Override
    public String toString() {
        return "PizzaModel{" +
                "pizzaId=" + pizzaId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", image='" + image + '\'' +
                '}';
    }
}


