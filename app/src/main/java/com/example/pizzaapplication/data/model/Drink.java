package com.example.pizzaapplication.data.model;

import com.google.gson.annotations.SerializedName;
import java.math.BigDecimal;
import java.util.List;

public class Drink {

    @SerializedName("drinkId")
    private int drinkId;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("price")
    private BigDecimal price;

    @SerializedName("image")
    private String image;

    @SerializedName("customerDrinks")
    private List<CustomerDrink> customerDrinks;

    // Default constructor
    public Drink() {
        this.drinkId = 0;
        this.name = "";
        this.description = "";
        this.price = BigDecimal.ZERO;
        this.image = "";
        this.customerDrinks = null; // or initialize as new ArrayList<>();
    }

    // Parameterized constructor
    public Drink(int drinkId, String name, String description, BigDecimal price, String image, List<CustomerDrink> customerDrinks) {
        this.drinkId = drinkId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
        this.customerDrinks = customerDrinks;
    }

    // Getters and Setters
    public int getDrinkId() {
        return drinkId;
    }

    public void setDrinkId(int drinkId) {
        this.drinkId = drinkId;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<CustomerDrink> getCustomerDrinks() {
        return customerDrinks;
    }

    public void setCustomerDrinks(List<CustomerDrink> customerDrinks) {
        this.customerDrinks = customerDrinks;
    }

    // toString method to print Drink object details
    @Override
    public String toString() {
        return "Drink{" +
                "drinkId=" + drinkId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", image='" + image + '\'' +
                ", customerDrinks=" + customerDrinks +
                '}';
    }
}
