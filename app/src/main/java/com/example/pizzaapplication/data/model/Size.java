package com.example.pizzaapplication.data.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Size {

    @SerializedName("sizeId")
    private int sizeId;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("price")
    private double price;

    @SerializedName("customerPizzas")
    private List<CustomerPizza> customerPizzas;

    // Default constructor
    public Size() {
        this.sizeId = 0;
        this.name = "";
        this.description = "";
        this.price = 0.0;
        this.customerPizzas = null;
    }

    // Parameterized constructor
    public Size(int sizeId, String name, String description, double price, List<CustomerPizza> customerPizzas) {
        this.sizeId = sizeId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.customerPizzas = customerPizzas;
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

    public List<CustomerPizza> getCustomerPizzas() {
        return customerPizzas;
    }

    public void setCustomerPizzas(List<CustomerPizza> customerPizzas) {
        this.customerPizzas = customerPizzas;
    }

    // toString method to print Size object details
    @Override
    public String toString() {
        return "Size{" +
                "sizeId=" + sizeId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", customerPizzas=" + customerPizzas +
                '}';
    }
}
