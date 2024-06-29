package com.example.pizzaapplication.data.model.Response;

import com.example.pizzaapplication.data.model.Order;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderHistoryResponseModel {
    @SerializedName("orderId")
    private int orderId;

    @SerializedName("totalAmount")
    private double totalAmount;

    @SerializedName("orderDate")
    private String orderDate;

    @SerializedName("image")
    private String image;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
