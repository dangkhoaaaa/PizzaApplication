package com.example.pizzaapplication.data.model.Response;

import com.google.gson.annotations.SerializedName;

public class BillDetailModel {

    @SerializedName("name")
    private String name;

    @SerializedName("quantity")
    private Integer quantity;

    @SerializedName("price")
    private Double price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
