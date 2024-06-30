package com.example.pizzaapplication.data.model;

public class Pizza {
    private String name;
    private String size;
    private String topping;
    private String img;
    private int quantity;
    private double price;

    public Pizza(String name, String size, String topping, String img, int quantity, double price) {
        this.name = name;
        this.size = size;
        this.topping = topping;
        this.img = img;
        this.quantity = quantity;
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Pizza() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getTopping() {
        return topping;
    }

    public void setTopping(String topping) {
        this.topping = topping;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

