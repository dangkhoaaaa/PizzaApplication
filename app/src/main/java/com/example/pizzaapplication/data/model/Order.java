package com.example.pizzaapplication.data.model;

public class Order {
    private int orderId;
    private double totalAmount;
    private String orderDate;
    private String image;

    public Order() {
    }

    public Order(int orderId, double totalAmount, String orderDate, String image) {
        this.orderId = orderId;
        this.totalAmount = totalAmount;
        this.orderDate = orderDate;
        this.image = image;
    }

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

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", totalAmount=" + totalAmount +
                ", orderDate='" + orderDate + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
