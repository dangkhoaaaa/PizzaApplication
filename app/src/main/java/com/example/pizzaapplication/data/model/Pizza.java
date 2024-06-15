package com.example.pizzaapplication.data.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Pizza {

    @SerializedName("pizzaId")
    private int pizzaId;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("price")
    private double price;

    @SerializedName("image")
    private String image;

    // Assuming there's a CustomerPizza class defined elsewhere
    @SerializedName("customerPizzas")
    private List<CustomerPizza> customerPizzas;

    // Default constructor
    public Pizza() {
        this.pizzaId = 0;
        this.name = "";
        this.description = "";
        this.price = 0.0;
        this.image = "";
        this.customerPizzas = null; // or initialize as new ArrayList<>();
    }

    // Parameterized constructor
    public Pizza(int pizzaId, String name, String description, double price, String image, List<CustomerPizza> customerPizzas) {
        this.pizzaId = pizzaId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
        this.customerPizzas = customerPizzas;
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

    public List<CustomerPizza> getCustomerPizzas() {
        return customerPizzas;
    }

    public void setCustomerPizzas(List<CustomerPizza> customerPizzas) {
        this.customerPizzas = customerPizzas;
    }

    // toString method to print Pizza object details
    @Override
    public String toString() {
        return "Pizza{" +
                "pizzaId=" + pizzaId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", image='" + image + '\'' +
                ", customerPizzas=" + customerPizzas +
                '}';
    }
}
