package com.example.pizzaapplication.data.model.Response;

import com.example.pizzaapplication.data.model.TopOrder;
import com.google.gson.annotations.SerializedName;

public class DashboardResponse {
    @SerializedName("total-users")
    private int totalUsers;

    @SerializedName("total-orders")
    private int totalOrders;

    @SerializedName("total-revenue")
    private double totalRevenue;

    @SerializedName("top-1-pizza-order")
    private TopOrder topPizzaOrder;

    @SerializedName("top-1-drink-order")
    private TopOrder topDrinkOrder;

    // Getters and setters for each field

    public int getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(int totalUsers) {
        this.totalUsers = totalUsers;
    }

    public int getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(int totalOrders) {
        this.totalOrders = totalOrders;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public TopOrder getTopPizzaOrder() {
        return topPizzaOrder;
    }

    public void setTopPizzaOrder(TopOrder topPizzaOrder) {
        this.topPizzaOrder = topPizzaOrder;
    }

    public TopOrder getTopDrinkOrder() {
        return topDrinkOrder;
    }

    public void setTopDrinkOrder(TopOrder topDrinkOrder) {
        this.topDrinkOrder = topDrinkOrder;
    }
}

