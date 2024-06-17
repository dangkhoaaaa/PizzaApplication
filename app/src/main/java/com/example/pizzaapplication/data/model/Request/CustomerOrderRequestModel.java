package com.example.pizzaapplication.data.model.Request;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class CustomerOrderRequestModel {

    @SerializedName("orderId")
    private int orderId;

    @SerializedName("userId")
    private int userId;

    @SerializedName("totalAmount")
    private double totalAmount;

    @SerializedName("status")
    private int status;

    @SerializedName("customerDrinks")
    private List<CustomerDrinkRequestModel> customerDrinks;

    @SerializedName("customerPizzas")
    private List<CustomerPizzaRequestModel> customerPizzas;

    // Default constructor
    public CustomerOrderRequestModel() {
        this.orderId = 0;
        this.userId = 0;
        this.totalAmount = 0.0;
        this.status = 0;
        this.customerDrinks = null;
        this.customerPizzas = null;
    }

    // Parameterized constructor
    public CustomerOrderRequestModel(int orderId, int userId, double totalAmount, int status,
                                     List<CustomerDrinkRequestModel> customerDrinks,
                                     List<CustomerPizzaRequestModel> customerPizzas) {
        this.orderId = orderId;
        this.userId = userId;
        this.totalAmount = totalAmount;
        this.status = status;
        this.customerDrinks = customerDrinks;
        this.customerPizzas = customerPizzas;
    }

    // Getters and Setters
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<CustomerDrinkRequestModel> getCustomerDrinks() {
        return customerDrinks;
    }

    public void setCustomerDrinks(List<CustomerDrinkRequestModel> customerDrinks) {
        this.customerDrinks = customerDrinks;
    }

    public List<CustomerPizzaRequestModel> getCustomerPizzas() {
        return customerPizzas;
    }

    public void setCustomerPizzas(List<CustomerPizzaRequestModel> customerPizzas) {
        this.customerPizzas = customerPizzas;
    }

    // toString method to print CustomerOrder object details
    @Override
    public String toString() {
        return "CustomerOrderRequestModel{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", totalAmount=" + totalAmount +
                ", status=" + status +
                ", customerDrinks=" + customerDrinks +
                ", customerPizzas=" + customerPizzas +
                '}';
    }
}
