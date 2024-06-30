package com.example.pizzaapplication.data.model;

import com.google.gson.annotations.SerializedName;
import java.math.BigDecimal;
import java.util.List;

public class Drink {
    private String name;
    private String img;
    private int quantity;
    private double price;

    public Drink() {
    }

    public Drink(String name, String img, int quantity, double price) {
        this.name = name;
        this.img = img;
        this.quantity = quantity;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
