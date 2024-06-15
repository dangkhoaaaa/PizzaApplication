package com.example.pizzaapplication.data.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Topping {

    @SerializedName("toppingId")
    private int toppingId;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("price")
    private double price;

    @SerializedName("customerPizzas")
    private List<CustomerPizza> customerPizzas;

    // Default constructor
    public Topping() {
        this.toppingId = 0;
        this.name = "";
        this.description = "";
        this.price = 0.0;
        this.customerPizzas = null;
    }

    // Parameterized constructor
    public Topping(int toppingId, String name, String description, double price, List<CustomerPizza> customerPizzas) {
        this.toppingId = toppingId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.customerPizzas = customerPizzas;
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

    public List<CustomerPizza> getCustomerPizzas() {
        return customerPizzas;
    }

    public void setCustomerPizzas(List<CustomerPizza> customerPizzas) {
        this.customerPizzas = customerPizzas;
    }

    // toString method to print Topping object details
    @Override
    public String toString() {
        return "Topping{" +
                "toppingId=" + toppingId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", customerPizzas=" + customerPizzas +
                '}';
    }
}
