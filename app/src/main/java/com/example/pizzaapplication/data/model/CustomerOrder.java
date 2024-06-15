package com.example.pizzaapplication.data.model;

import com.google.gson.annotations.SerializedName;
import java.math.BigDecimal;
import java.util.List;

public class CustomerOrder {

    @SerializedName("orderId")
    private int orderId;

    @SerializedName("userId")
    private int userId;

    @SerializedName("totalAmount")
    private BigDecimal totalAmount;

    @SerializedName("status")
    private int status;

    @SerializedName("user")
    private User user;

    @SerializedName("customerDrinks")
    private List<CustomerDrink> customerDrinks;

    @SerializedName("customerPizzas")
    private List<CustomerPizza> customerPizzas;

    // Default constructor
    public CustomerOrder() {
        this.orderId = 0;
        this.userId = 0;
        this.totalAmount = BigDecimal.ZERO;
        this.status = 0;
        this.user = null; // or initialize as new User();
        this.customerDrinks = null; // or initialize as new ArrayList<>();
        this.customerPizzas = null; // or initialize as new ArrayList<>();
    }

    // Parameterized constructor
    public CustomerOrder(int orderId, int userId, BigDecimal totalAmount, int status, User user, List<CustomerDrink> customerDrinks, List<CustomerPizza> customerPizzas) {
        this.orderId = orderId;
        this.userId = userId;
        this.totalAmount = totalAmount;
        this.status = status;
        this.user = user;
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

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<CustomerDrink> getCustomerDrinks() {
        return customerDrinks;
    }

    public void setCustomerDrinks(List<CustomerDrink> customerDrinks) {
        this.customerDrinks = customerDrinks;
    }

    public List<CustomerPizza> getCustomerPizzas() {
        return customerPizzas;
    }

    public void setCustomerPizzas(List<CustomerPizza> customerPizzas) {
        this.customerPizzas = customerPizzas;
    }

    // toString method to print CustomerOrder object details
    @Override
    public String toString() {
        return "CustomerOrder{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", totalAmount=" + totalAmount +
                ", status=" + status +
                ", user=" + user +
                ", customerDrinks=" + customerDrinks +
                ", customerPizzas=" + customerPizzas +
                '}';
    }
}
